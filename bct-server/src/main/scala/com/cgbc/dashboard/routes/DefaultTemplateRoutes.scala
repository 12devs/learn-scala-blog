package com.cgbc.dashboard.routes

import akka.http.scaladsl.server._
import com.cgbc.dashboard.auth.JwtTokenService
import com.cgbc.dashboard.domain._
import com.cgbc.dashboard.services.DefaultTemplateService
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import monix.execution.Scheduler.Implicits.global
import org.json4s.Formats
import org.json4s.jackson.Serialization

trait DefaultTemplateRoutes {

  import Directives._
  import Json4sSupport._

  implicit val serialization: Serialization.type

  val defaultTemplateService: DefaultTemplateService
  val jwtTokenService: JwtTokenService
  implicit val formats: Formats

  lazy val defaultTemplateRoutes: Route = logRequestResult("api") {
    userRoutes ~ adminRoutes
  }

  private val userRoutes: Route = {
    jwtTokenService.auth() { jwtClaims =>
      path("defaultTemplates" ~ PathEnd) {
        get {
          complete(defaultTemplateService.getDefaultTemplates.map(_.sortBy(_.templateName != "News & Analytics").sortBy(_.templateName != "Trade")).runAsync)
        }
      } ~
        path("defaultTemplateByName" / Segment ~ PathEnd) { templateName =>
          get {
            complete(defaultTemplateService.getDefaultTemplate(templateName).runAsync)
          }
        }

    }
  }
  private val adminRoutes: Route = {
    jwtTokenService.auth(Some(AdminUserLevel)) { jwtClaims =>
      path("defaultTemplate" ~ PathEnd) {
        put {
          entity(as[NewDefaultTemplate]) { newTemplate =>
            complete(defaultTemplateService.createDefaultTemplate(newTemplate).runAsync)
          }
        } ~
          post {
            entity(as[DefaultTemplate]) { template =>
              complete(defaultTemplateService.updateDefaultTemplate(template).runAsync)
            }
          }
      } ~
        path("defaultTemplate" / Segment ~ PathEnd) { defaultTemplateId =>
          delete {
            complete(defaultTemplateService.deleteDefaultTemplate(DefaultTemplateId(defaultTemplateId)).map(_ => "").runAsync)
          }
        }
    }
  }

}
