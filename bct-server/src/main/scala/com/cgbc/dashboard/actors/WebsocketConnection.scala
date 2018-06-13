package com.cgbc.dashboard.actors

import akka.actor.{Actor, ActorRef, Props}
import com.cgbc.dashboard.actors.WebsocketConnection.{InboundMessage, OutboundMessage, OutputActor}
import com.cgbc.dashboard.auth.{JwtClaims, JwtTokenService}
import com.cgbc.dashboard.services.ConfigurationService
import com.typesafe.scalalogging.StrictLogging
import org.json4s.{JObject, _}
import org.json4s.jackson.JsonMethods._
import org.json4s.JsonDSL._

import scala.util.Try

case class ConnectionRequest(jwtToken: String, currentBuildNumber: Int)

case class ParsedConnectionRequest(claims: JwtClaims, currentBuildNumber: Int)

class WebsocketConnection(jwtTokenService: JwtTokenService, configurationService: ConfigurationService, websocketSupervisor: ActorRef) extends Actor with StrictLogging {

  implicit val formats: Formats = DefaultFormats

  override def receive: Receive = initializing(None, None)

  private def initializing(outputActorOpt: Option[ActorRef], requestOpt: Option[ParsedConnectionRequest]): Receive = {
    case e: OutputActor =>
      // If the outgoing pipe goes down, we will want to stop
      context.watch(e.outputActor)
      // If we already have auth information, start up. Otherwise, hold onto this pipe information.
      requestOpt match {
        case Some(claims) =>
          run(e.outputActor, claims)
        case None =>
          context.become(initializing(Some(e.outputActor), None))
      }
    case InboundMessage(message) =>
      val requestOpt = for {
        connectionRequest <- Try(parse(message).extract[ConnectionRequest]).toOption
        claims <- jwtTokenService.claimsFromToken(connectionRequest.jwtToken)
      } yield ParsedConnectionRequest(claims, connectionRequest.currentBuildNumber)

      // The first message we receive from the user should be a JWT string
      (outputActorOpt, requestOpt) match {
        case (Some(output), Some(request)) =>
          run(output, request)
        case (None, Some(request)) =>
          context.become(initializing(None, Some(request)))
        case (Some(output), None) =>
          context.stop(self)
          context.stop(output)
        case (None, None) =>
          context.stop(self)
      }
  }

  private def run(output: ActorRef, request: ParsedConnectionRequest): Unit = {
    websocketSupervisor ! WebsocketSupervisor.RegisterConnection(request)
    output ! OutboundMessage(s"""{"version": ${configurationService.currentBuildNumber}}""")

    context.become(connected(output))
  }

  private def connected(output: ActorRef): Receive = {
    case InboundMessage(_) => Unit
    case WebsocketSupervisor.MessageBroadcast(message) =>
      output ! OutboundMessage(message)
  }
}

object WebsocketConnection {

  case class OutputActor(outputActor: ActorRef)

  case class InboundMessage(text: String)

  case class OutboundMessage(text: String)

  def props(jwtTokenService: JwtTokenService, configurationService: ConfigurationService, websocketSupervisor: ActorRef): Props = {
    Props(new WebsocketConnection(jwtTokenService, configurationService, websocketSupervisor))
  }
}
