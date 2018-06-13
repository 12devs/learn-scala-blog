package com.cgbc.dashboard.startup

import akka.actor.{Actor, ActorLogging, ActorRef, Cancellable, PoisonPill, Props}
import com.cgbc.dashboard.startup.StartupMessage._

import scala.concurrent.duration._

class StartupManager extends Actor with ActorLogging {

  import context.dispatcher

  override def receive: Receive = {
    case Initialise(props) =>
      nextProcess(props, sender())

    case _ =>
  }

  private def processing(child: ActorRef, startupProps: List[Props], initiator: ActorRef, tickTimer: Cancellable): Receive = {
    case Completed =>
      tickTimer.cancel()
      child ! PoisonPill
      nextProcess(startupProps, initiator)
  }

  private def nextProcess(startupProps: List[Props], initiator: ActorRef): Unit = {
    startupProps.headOption match {
      case Some(props) =>
        val child = context.system.actorOf(props)
        child ! Begin(self)
        val tickTimer = context.system.scheduler.schedule(5.seconds, 5.seconds, child, Tick)
        context.become(processing(child, startupProps.tail, initiator, tickTimer))

      case None =>
        initiator ! "Completed"
        self ! PoisonPill
    }
  }

}

trait StartupMessage

case object StartupMessage {

  case class Initialise(props: List[Props]) extends StartupMessage

  case object Tick extends StartupMessage

  case class Begin(manager: ActorRef) extends StartupMessage

  case object Completed extends StartupMessage

}