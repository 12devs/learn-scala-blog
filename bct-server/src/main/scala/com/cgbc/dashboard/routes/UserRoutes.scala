package com.cgbc.dashboard.routes

import akka.http.scaladsl.server._
import com.cgbc.dashboard.auth.JwtTokenService
import com.cgbc.dashboard.domain._
import com.cgbc.dashboard.services.{TemplateService, UserService}
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import monix.execution.Scheduler.Implicits.global
import org.json4s.Formats
import org.json4s.jackson.Serialization

trait UserRoutes {

  import Directives._
  import Json4sSupport._

  implicit val serialization: Serialization.type
  val userService: UserService
  val templateService: TemplateService
  val jwtTokenService: JwtTokenService
  implicit val formats: Formats

  lazy val userRoutes: Route = logRequestResult("api") {
    jwtTokenService.auth(Some(AdminUserLevel)) { jwtClaims =>
      path("users" ~ PathEnd) {
        get {
          complete(userService.getUsers.runAsync)
        }
      } ~
        path("user" ~ PathEnd) {
          post {
            entity(as[NewUser]) { newUser =>
              complete {
                userService.createUser(newUser)
                  .flatMap(templateService.addDefaultTemplatesForUser)
                  .runAsync
              }
            }
          } ~
            put {
              entity(as[User]) { user =>
                complete(userService.updateUser(user).runAsync)
              }
            }
        } ~
        path("user" / Segment ~ PathEnd) { userId =>
          delete {
            complete(userService.deleteUser(UserId(userId)).map(_ => "").runAsync)
          }
        }
    }
  }
}
