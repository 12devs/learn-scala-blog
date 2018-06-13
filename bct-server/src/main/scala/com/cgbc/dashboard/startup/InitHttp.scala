package com.cgbc.dashboard.startup

import akka.actor.Status.Failure
import akka.actor.{Actor, ActorLogging, ActorRef}
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.server.Route
import akka.pattern.pipe
import akka.stream.ActorMaterializer
import com.cgbc.dashboard.HttpConfig
import com.cgbc.dashboard.startup.StartupMessage.{Begin, Completed, Tick}

class InitHttp(implicit mat: ActorMaterializer, httpServerConfig: HttpConfig, routes: Route) extends Actor with ActorLogging {

  import context.dispatcher

  override def receive: Receive = {
    case Begin(parent) =>
      log.info("Initialise HTTP server begin")
      startHttpServer(parent)
      context.become(waitingForServer(parent))
  }

  private def waitingForServer(parent: ActorRef): Receive = {
    case Tick =>
      startHttpServer(parent)

    case _: ServerBinding =>
      parent ! Completed
      log.info("Initialise HTTP server complete")

    case Failure(t) =>
      t.printStackTrace()
  }

  private def startHttpServer(parent: ActorRef): Unit = {
    val server = Http(context.system).bindAndHandle(routes, httpServerConfig.host, httpServerConfig.port)
    pipe(server).pipeTo(self)
  }
}