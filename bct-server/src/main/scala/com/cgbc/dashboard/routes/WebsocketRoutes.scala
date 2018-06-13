package com.cgbc.dashboard.routes

import akka.NotUsed
import akka.actor.{ActorRef, ActorSystem, PoisonPill}
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server._
import akka.stream.scaladsl.{Flow, Sink, Source}
import akka.stream.{ActorMaterializer, OverflowStrategy}
import com.cgbc.dashboard.actors._
import com.cgbc.dashboard.auth.JwtTokenService
import com.cgbc.dashboard.services.ConfigurationService
import com.typesafe.scalalogging.StrictLogging
import org.json4s.Formats
import org.json4s.jackson.Serialization

import scala.concurrent.ExecutionContext

trait WebsocketRoutes extends StrictLogging {

  import Directives._

  implicit val serialization: Serialization.type

  val jwtTokenService: JwtTokenService
  val websocketSupervisor: ActorRef
  val configurationService: ConfigurationService
  implicit val formats: Formats
  implicit val system: ActorSystem
  implicit val executionContext: ExecutionContext
  implicit val mat: ActorMaterializer = ActorMaterializer()

  lazy val websocketRoutes: Route = logRequestResult("api") {
    path("ws" ~ PathEnd) {
      handleWebSocketMessages(newUser())
    }
  }

  private def newUser(): Flow[Message, Message, NotUsed] = {
    val connectedWsActor = system.actorOf(WebsocketConnection.props(jwtTokenService, configurationService, websocketSupervisor))

    val incomingMessages: Sink[Message, NotUsed] =
      Flow[Message].map {
        case TextMessage.Strict(text) => WebsocketConnection.InboundMessage(text)
      }.to(Sink.actorRef(connectedWsActor, PoisonPill))

    val outgoingMessages: Source[Message, NotUsed] =
      Source
        .actorRef[WebsocketConnection.OutboundMessage](10, OverflowStrategy.fail)
        .mapMaterializedValue { outputActor =>
          connectedWsActor ! WebsocketConnection.OutputActor(outputActor)
          NotUsed
        }
        .map {
          case WebsocketConnection.OutboundMessage(text) => TextMessage(text)
        }

    Flow.fromSinkAndSource(incomingMessages, outgoingMessages)
  }
}
