package com.cgbc.dashboard.startup

import akka.actor.{Actor, ActorLogging, ActorRef}
import com.cgbc.dashboard.DatabaseConfig
import com.cgbc.dashboard.startup.StartupMessage.{Begin, Completed, Tick}
import org.flywaydb.core.Flyway
import scalikejdbc.config.DBs

class InitDatabase(databaseConfig: DatabaseConfig) extends Actor with ActorLogging {

  override def receive: Receive = {
    case Begin(parent) =>
      log.info("Initialise database begin")
      try {
        initialiseDatabase(parent)
      } catch {
        case e: Throwable =>
          e.printStackTrace()
          context.become(waitingForTick(parent))
      }
  }

  private def waitingForTick(parent: ActorRef): Receive = {
    case Tick =>
      try {
        initialiseDatabase(parent)
      } catch {
        case e: Throwable =>
          e.printStackTrace()
      }
  }

  private def initialiseDatabase(parent: ActorRef): Unit = {
    val flyway = new Flyway()
    flyway.setDataSource(databaseConfig.url, databaseConfig.user, databaseConfig.password)
    flyway.migrate()

    DBs.setupAll()

    parent ! Completed

    log.info("Initialise database complete")
  }

}