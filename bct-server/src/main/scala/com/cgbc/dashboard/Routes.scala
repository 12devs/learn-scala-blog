package com.cgbc.dashboard

import akka.http.scaladsl.server._
import com.cgbc.dashboard.routes._
import com.cgbc.dashboard.utils.json.AllSerializers
import org.json4s.jackson

trait Routes
  extends AllSerializers
    with AdminRoutes
    with AuthRoutes
    with ComponentRoutes
    with DebugRoutes
    with DefaultTemplateRoutes
    with UserAppRoutes
    with UserRoutes
    with UserTemplateRoutes
    with WebsocketRoutes
    with ArticleRoutes
    with CommentRoutes {

  import Directives._

  implicit val serialization = jackson.Serialization

  lazy val routes: Route = logRequestResult("api") {
    pathPrefix("api" / "1") {
      adminRoutes ~
        authRoutes ~
        componentRoutes ~
        debugRoutes ~
        defaultTemplateRoutes ~
        userAppRoutes ~
        userRoutes ~
        userTemplateRoutes ~
        websocketRoutes ~
        articleRoutes ~
        commentRoutes
    }
  }
}
