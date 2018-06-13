package com.cgbc.dashboard.routes

import akka.http.scaladsl.server._
import com.cgbc.dashboard.auth.JwtTokenService
import com.cgbc.dashboard.services.{ConfigurationService, TemplateService, UserService}
import com.typesafe.scalalogging.StrictLogging
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global
import org.json4s.Formats
import org.json4s.jackson.Serialization

import scala.concurrent.ExecutionContext
import scala.util.Success

trait AuthRoutes extends StrictLogging {

  import Directives._
  import Json4sSupport._

  implicit val serialization: Serialization.type

  val jwtTokenService: JwtTokenService
  val userService: UserService
  val templateService: TemplateService
  val configurationService: ConfigurationService
  implicit val formats: Formats
  implicit val executionContext: ExecutionContext

  lazy val authRoutes: Route = logRequestResult("api") {
    path("auth" / "byHeader" ~ PathEnd) {
      post {
        headerValueByName("X-Ssl-Client-Cn") { clientCn =>
          val result = userService.getOrCreateUser(clientCn)
            .flatMap {
              case (user, true) => templateService.addDefaultTemplatesForUser(user)
              case (user, false) => Task.now(user)
            }.runAsync
          onComplete(result) {
            case Success(user) => complete(Map("jwtToken" -> jwtTokenService.newToken(user)))
            case _ => reject(AuthorizationFailedRejection)
          }
        }
      }
    }
  }

}
