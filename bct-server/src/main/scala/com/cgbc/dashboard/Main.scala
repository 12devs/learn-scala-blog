package com.cgbc.dashboard

import java.util.Locale
import java.util.concurrent.TimeUnit

import akka.actor.{ActorSystem, Props}
import akka.pattern._
import akka.stream.{ActorMaterializer, ActorMaterializerSettings, Supervision}
import akka.util.Timeout
import com.cgbc.dashboard.startup.{InitDatabase, InitHttp, StartupManager, StartupMessage}
import com.typesafe.scalalogging.StrictLogging
import scalikejdbc.config.DBs

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

object Main extends App with StrictLogging {
  Locale.setDefault(Locale.US)

  val config: AppConfig = pureconfig.loadConfigOrThrow[AppConfig]

  val decider: Supervision.Decider = { e =>
    logger.error("Unhandled exception in stream", e)
    Supervision.Resume
  }

  implicit val _system: ActorSystem = ActorSystem()
  val materializerSettings: ActorMaterializerSettings = ActorMaterializerSettings(_system).withSupervisionStrategy(decider)
  implicit val _materializer: ActorMaterializer = ActorMaterializer(materializerSettings)(_system)

  import _system.dispatcher

  val modules = new DependencyWiring with Routes {
    lazy val system: ActorSystem = _system
    lazy val materializer: ActorMaterializer = _materializer
    lazy val executionContext: ExecutionContext = _system.dispatcher
    import scala.concurrent.duration._
    val timeout: Timeout = Timeout(5.seconds)
    lazy val appConfig: AppConfig = config
  }

  val startupSequence = List(
    Props(classOf[InitDatabase], modules.appConfig.db.default),
    Props(classOf[InitHttp], _materializer, modules.appConfig.http, modules.routes)
  )
  val startupFuture = (_system.actorOf(Props[StartupManager]) ? StartupMessage.Initialise(startupSequence))(Timeout(21474835, TimeUnit.SECONDS))

  startupFuture.onComplete {
    case Success(_) =>
      logger.info("Server successfully started")
      sys.addShutdownHook {
        DBs.closeAll()
        modules.system.terminate()
        logger.info("Server stopped")
      }

    case Failure(t) =>
      logger.error("Unable to start server", t)
      sys.addShutdownHook {
        DBs.closeAll()
        modules.system.terminate()
        logger.info("Server stopped")

        
      }
  }
}
