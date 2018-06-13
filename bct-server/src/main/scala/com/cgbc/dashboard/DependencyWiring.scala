package com.cgbc.dashboard

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.stream.Materializer
import com.cgbc.dashboard.actors.WebsocketSupervisor
import com.cgbc.dashboard.auth.JwtTokenService
import com.cgbc.dashboard.services._

import scala.concurrent.ExecutionContext

trait DependencyWiring {
  def system: ActorSystem

  def materializer: Materializer

  implicit val executionContext: ExecutionContext

  val appConfig: AppConfig

  lazy val componentService: ComponentService = new ComponentService

  lazy val configurationService: ConfigurationService = new ConfigurationService

  lazy val defaultTemplateService: DefaultTemplateService = new DefaultTemplateService

  lazy val jwtTokenService: JwtTokenService = new JwtTokenService

  lazy val templateService: TemplateService = new TemplateService

  lazy val userAppService: UserAppService = new UserAppService

  lazy val userService: UserService = new UserService

  lazy val userTemplateService: UserTemplateService = new UserTemplateService

  lazy val websocketSupervisor: ActorRef = system.actorOf(WebsocketSupervisor.props(configurationService))

  lazy val articleService: ArticleService = new ArticleService

  lazy val commentService: CommentService = new CommentService
}
