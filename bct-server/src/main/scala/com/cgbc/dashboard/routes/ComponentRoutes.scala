package com.cgbc.dashboard.routes

import akka.http.scaladsl.server._
import com.cgbc.dashboard.auth.JwtTokenService
import com.cgbc.dashboard.domain._
import com.cgbc.dashboard.services.ComponentService
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import monix.execution.Scheduler.Implicits.global
import org.json4s.Formats
import org.json4s.jackson.Serialization

trait ComponentRoutes {

  import Directives._
  import Json4sSupport._

  implicit val serialization: Serialization.type

  val componentService: ComponentService
  val jwtTokenService: JwtTokenService
  implicit val formats: Formats

  lazy val componentRoutes: Route = logRequestResult("api") {
    userRoutes ~ adminRoutes
  }

  private val userRoutes = {
    jwtTokenService.auth() { jwtClaims =>
      path("components" / "all" ~ PathEnd) {
        get {
          complete(componentService.getComponents().runAsync)
        }
      } ~
      path("components" / "forApp" / Segment ~ PathEnd) { appId =>
        get {
          complete(componentService.getComponentsForApp(AppId(appId)).runAsync)
        }
      } ~
        path("components" / "appsWithComponents" ~ PathEnd) {
          get {
            complete(componentService.getAppsWithComponents().runAsync)
          }
        }
    }
  }

  private val adminRoutes = {
    jwtTokenService.auth(Some(AdminUserLevel)) { jwtClaims =>
      path("component" ~ PathEnd) {
        post {
          entity(as[NewComponent]) { newComponent =>
            complete(componentService.createComponent(newComponent).runAsync)
          }
        } ~
          put {
            entity(as[Component]) { component =>
              complete(componentService.updateComponent(component).runAsync)
            }
          }
      } ~
        path("component" / Segment ~ PathEnd) { componentId =>
          delete {
            complete(componentService.deleteComponent(ComponentId(componentId)).map(_ => "").runAsync)
          }
        }
    }
  }

}
