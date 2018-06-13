package com.cgbc.dashboard.routes

import akka.actor.ActorRef
import akka.http.scaladsl.server._
import com.cgbc.dashboard.actors.WebsocketSupervisor._
import com.cgbc.dashboard.auth.JwtTokenService
import com.cgbc.dashboard.domain.{AdminUserLevel, AppId, UserId}
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s.Formats
import org.json4s.jackson.Serialization
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.ExecutionContext

trait AdminRoutes {

  import Directives._
  import Json4sSupport._

  implicit val serialization: Serialization.type

 // val appService: AppService
  val jwtTokenService: JwtTokenService
  implicit val formats: Formats
  val websocketSupervisor: ActorRef
  implicit val executionContext: ExecutionContext
  implicit val timeout: Timeout

  lazy val adminRoutes: Route = logRequestResult("api") {
    jwtTokenService.auth(Some(AdminUserLevel)) { jwtClaims =>
      path("admin" / "reload") {
        post {
          websocketSupervisor ! RefreshAll()
          complete("")
        }
      } ~ path("admin" / "reload" / "outdated") {
        post {
          websocketSupervisor ! RefreshOutdated()
          complete("")
        }
      } ~ path("admin" / "reload" / "user" / Segment ~ PathEnd) { userId =>
        post {
          websocketSupervisor ! RefreshUser(UserId(userId))
          complete("")
        }
      } ~ path("admin" / "reload" / "app" / Segment ~ PathEnd) { appId =>
        post {
          websocketSupervisor ! RefreshApp(AppId(appId))
          complete("")
        }
      } ~ path("admin" / "connections") {
        get {
          val f = websocketSupervisor ? GetConnections() map {
            case GetConnectionsResponse(connections) =>
              connections.sortBy(connection => (connection.claims.firstName, connection.claims.lastName))
          }
          complete(f)
        }
      }
    }
  }

}
