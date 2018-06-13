package com.cgbc.dashboard.routes

import akka.http.scaladsl.server._
import com.cgbc.dashboard.auth.JwtTokenService
import com.cgbc.dashboard.domain._
import com.cgbc.dashboard.services.UserAppService
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import monix.execution.Scheduler.Implicits.global
import org.json4s.Formats
import org.json4s.jackson.Serialization

trait UserAppRoutes {

  import Directives._
  import Json4sSupport._

  implicit val serialization: Serialization.type

  val userAppService: UserAppService
  val jwtTokenService: JwtTokenService
  implicit val formats: Formats

  lazy val userAppRoutes: Route = logRequestResult("api") {
    userRoutes ~ adminRoutes
  }

  private val userRoutes: Route = {
    jwtTokenService.auth() { jwtClaims =>
      path("userApps" / "byUser" / Segment ~ PathEnd) { userId =>
        get {
          complete(userAppService.getUserApps(UserId(userId)).runAsync)
        }
      }
    }
  }

  private val adminRoutes: Route = {
    jwtTokenService.auth(Some(AdminUserLevel)) { jwtClaims =>
      path("userApps" / "byApp" / Segment ~ PathEnd) { appId =>
        get {
          complete(userAppService.getUserApps(AppId(appId)).runAsync)
        }
      } ~
        path("userApp" ~ PathEnd) {
          post {
            entity(as[UserApp]) { newUserApp =>
              complete(userAppService.createUserApp(newUserApp).runAsync)
            }
          } ~
            put {
              entity(as[UserApp]) { userApp =>
                complete(userAppService.updateUserApp(userApp).runAsync)
              }
            }
        } ~
        path("userApp" / Segment / Segment ~ PathEnd) { (userId, appId) =>
          delete {
            complete(userAppService.deleteUserApp(UserId(userId), AppId(appId)).map(_ => "").runAsync)
          }
        }
    }
  }
}
