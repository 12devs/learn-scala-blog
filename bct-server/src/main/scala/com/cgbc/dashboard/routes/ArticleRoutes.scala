package com.cgbc.dashboard.routes

import akka.http.scaladsl.server._
import com.cgbc.dashboard.auth.JwtTokenService
import com.cgbc.dashboard.domain._
import com.cgbc.dashboard.services.{ArticleService, TemplateService, UserService}
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import monix.execution.Scheduler.Implicits.global
import org.json4s.Formats
import org.json4s.jackson.Serialization

trait ArticleRoutes {

  import Directives._
  import Json4sSupport._

  implicit val serialization: Serialization.type
  val userService: UserService
  val templateService: TemplateService
  val jwtTokenService: JwtTokenService
  val articleService: ArticleService
  implicit val formats: Formats

  lazy val articleRoutes: Route = logRequestResult("api") {
    path("articles" ~ PathEnd) {
      get {
        complete(articleService.getArticle.runAsync)
      }
    } ~
      path("article" ~ PathEnd) {
        post {
          entity(as[NewArticle]) { newArticle =>
            complete {
              articleService.createArticle(newArticle)
                .runAsync
            }
          }
        } ~
          put {
            entity(as[Article]) { article =>
              complete(articleService.updateArticle(article).runAsync)
            }
          }
      } ~
      path("article" / Segment ~ PathEnd) { articleId =>
        delete {
          complete(articleService.deleteArticle(ArticleId(articleId)).map(_ => "").runAsync)
        }
      }

  }
}
