package com.cgbc.dashboard.routes

import akka.http.scaladsl.server._
import com.cgbc.dashboard.auth.JwtTokenService
import com.cgbc.dashboard.domain._
import com.cgbc.dashboard.services.UserTemplateService
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import monix.execution.Scheduler.Implicits.global
import org.json4s.Formats
import org.json4s.jackson.Serialization
import org.json4s.native.Serialization.write

trait UserTemplateRoutes {

  import Directives._
  import Json4sSupport._

  implicit val serialization: Serialization.type

  val userTemplateService: UserTemplateService

  val jwtTokenService: JwtTokenService

  implicit val formats: Formats

  lazy val userTemplateRoutes: Route = jwtTokenService.auth() { jwtClaims =>
    path("userTemplates" ~ PathEnd) {
      get {
        complete(userTemplateService.getUserTemplates(jwtClaims.userId).runAsync)
      }
    } ~
      path("userTemplate" ~ PathEnd) {
        put {
          entity(as[NewUserTemplate]) { newTemplate =>
            complete(userTemplateService.createUserTemplate(jwtClaims.userId, newTemplate).runAsync)
          }
        } ~
          post {
            entity(as[UserTemplateUpdateRequest]) { template =>
              complete(userTemplateService.updateUserTemplate(template.toUserTemplate(jwtClaims.userId)).runAsync)
            }
          }
      } ~
      path("userTemplateRename" ~ PathEnd) {
        post {
          entity(as[BasicUserTemplateUpdateRequest]) { template =>
            complete(userTemplateService.updateBasicUserTemplate(template.toBasicUserTemplate(jwtClaims.userId)).runAsync)
          }
        }
      } ~
      path("userTemplate" / Segment ~ PathEnd) { userTemplateId =>
          delete {
            complete(userTemplateService.deleteUserTemplate(jwtClaims.userId, UserTemplateId(userTemplateId)).map(_ => "").runAsync)
          }
      } ~
      path("userTemplateByName" / Segment ~ PathEnd) { templateName =>
        get {
          complete(userTemplateService.getUserTemplate(jwtClaims.userId, templateName).runAsync)
        }
      }
  }
}
