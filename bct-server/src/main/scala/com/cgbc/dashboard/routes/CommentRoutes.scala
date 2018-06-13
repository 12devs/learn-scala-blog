package com.cgbc.dashboard.routes

import akka.http.scaladsl.server._
import com.cgbc.dashboard.auth.JwtTokenService
import com.cgbc.dashboard.domain._
import com.cgbc.dashboard.services.{CommentService, TemplateService, UserService}
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import monix.execution.Scheduler.Implicits.global
import org.json4s.Formats
import org.json4s.jackson.Serialization

trait CommentRoutes {

  import Directives._
  import Json4sSupport._

  implicit val serialization: Serialization.type
  val userService: UserService
  val templateService: TemplateService
  val jwtTokenService: JwtTokenService
  val commentService: CommentService
  implicit val formats: Formats

  lazy val commentRoutes: Route = logRequestResult("api") {
    path("comments" ~ PathEnd) {
      get {
        complete(commentService.getComment.runAsync)
      }
    } ~
      path("comment" ~ PathEnd) {
        post {
          entity(as[NewComment]) { newComment =>
            complete {
              commentService.createComment(newComment)
                .runAsync
            }
          }
        } ~
          put {
            entity(as[Comment]) { comment =>
              complete(commentService.updateComment(comment).runAsync)
            }
          }
      } ~
      path("comment" / Segment ~ PathEnd) { commentId =>
        delete {
          complete(commentService.deleteComment(CommentId(commentId)).map(_ => "").runAsync)
        }
      }

  }
}
