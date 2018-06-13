package com.cgbc.dashboard.routes

import akka.actor.ActorRef
import akka.http.scaladsl.server._
import com.cgbc.dashboard.actors.WebsocketSupervisor.RedirectToDestination
import com.cgbc.dashboard.auth.JwtTokenService
import com.cgbc.dashboard.services.ConfigurationService
import com.typesafe.scalalogging.StrictLogging
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s.Formats
import org.json4s.jackson.Serialization

import scala.concurrent.ExecutionContext

case class Destination(url: String)

trait DebugRoutes extends StrictLogging {

  import Directives._
  import Json4sSupport._

  implicit val serialization: Serialization.type

  val jwtTokenService: JwtTokenService
  val websocketSupervisor: ActorRef
  val configurationService: ConfigurationService
  implicit val formats: Formats
  implicit val executionContext: ExecutionContext

  lazy val debugRoutes: Route = logRequestResult("api") {
    jwtTokenService.auth() { jwtClaims =>
      path("debug" / "switchSystem" ~ PathEnd) {
        post {
          entity(as[Destination]) { destination =>
            websocketSupervisor ! RedirectToDestination(jwtClaims.userId, destination.url)
            complete("")
          }
        }
      }
    }

  }
}
