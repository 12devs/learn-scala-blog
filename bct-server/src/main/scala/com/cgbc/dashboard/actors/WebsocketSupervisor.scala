package com.cgbc.dashboard.actors

import akka.actor.{Actor, ActorRef, Cancellable, Props, Terminated}
import com.cgbc.dashboard.actors.WebsocketSupervisor._
import com.cgbc.dashboard.auth.JwtClaims
import com.cgbc.dashboard.domain.{AppId, UserId}
import com.cgbc.dashboard.services.ConfigurationService
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

class WebsocketSupervisor(configurationService: ConfigurationService) extends Actor with StrictLogging {
  private implicit val ec: ExecutionContext = context.dispatcher
  private val tick: Cancellable = context.system.scheduler.schedule(10.second, 10.second, self, Tick())

  var connections: Map[ActorRef, ParsedConnectionRequest] = Map.empty

  override def receive: Receive = {
    case RegisterConnection(jwtClaims) =>
      connections += sender() -> jwtClaims
      logger.info(s"Connection registered (${connections.size} connections)")
      context.watch(sender())

    case RefreshAll() =>
      logger.info(s"Received refresh all")
      connections.keys.foreach(_ ! MessageBroadcast("Reload"))

    case RefreshUser(userId) =>
      logger.info(s"Received refresh user")
      connections.filter(_._2.claims.userId == userId).keys.foreach(_ ! MessageBroadcast("Reload"))

    case RefreshApp(appId) =>
      logger.info(s"Received refresh app: " + appId)
      connections.keys.foreach(_ ! MessageBroadcast(s"""{"type":"refreshApp", "appId":"${appId.id}"}"""))

    case SendNotificationsAvailable(userId) =>
      logger.info(s"Received send notifications")
      connections.filter(_._2.claims.userId == userId).keys.foreach(_ ! MessageBroadcast("Notifications"))

    case RedirectToDestination(userId, url) =>
      logger.info(s"Received redirect to destination $userId $url")
      connections.filter(_._2.claims.userId == userId).keys
        .foreach(_ ! MessageBroadcast(s"""{"type":"redirect", "url":"$url"}"""))

    case RefreshOutdated() =>
      logger.info(s"Received refresh outdated")
      connections
        .toList
        .filter(_._2.currentBuildNumber != configurationService.currentBuildNumber)
        .foreach(_._1 ! MessageBroadcast("Reload"))

    case Tick() =>
//      logger.info("Sending heartbeat")
      connections.keys foreach (_ ! MessageBroadcast("""{"type":"heartbeat"}"""))

    case GetConnections() =>
      sender ! GetConnectionsResponse(connections.values.toList)

    case Terminated(user) =>
      connections -= user
      logger.info(s"Connection removed (${connections.size} connections)")
  }
}

object WebsocketSupervisor {

  def props(configurationService: ConfigurationService): Props = {
    Props(new WebsocketSupervisor(configurationService))
  }

  case class RegisterConnection(request: ParsedConnectionRequest)

  case class RefreshAll()

  case class RefreshOutdated()

  case class RefreshUser(userId: UserId)

  case class RefreshApp(appId: AppId)

  case class MessageBroadcast(message: String)

  case class SendNotificationsAvailable(userId: UserId)

  case class RedirectToDestination(userId: UserId, url: String)

  case class Tick()

  case class GetConnections()

  case class GetConnectionsResponse(connections: List[ParsedConnectionRequest])

}
