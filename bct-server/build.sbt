name := "bct-server"

version := "0.1"

scalaVersion := "2.12.4"

scalacOptions += "-Ypartial-unification"

libraryDependencies ++= {
  val akkaVersion = "2.5.8"
  val akkaHttpVersion = "10.0.11"

  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,

    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-core" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,

    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,

    "org.slf4j" % "slf4j-api" % "1.7.+",
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",

    "org.typelevel" %% "cats-core" % "1.0.1",
    "org.typelevel" %% "cats-effect" % "0.8",
    "io.monix" %% "monix" % "3.0.0-M3",

    "com.github.pureconfig" %% "pureconfig" % "0.9.0",

    "com.auth0" % "java-jwt" % "3.1.0",

    "org.flywaydb" % "flyway-core" % "5.0.7",
    "org.scalikejdbc" %% "scalikejdbc" % "3.2.0",
    "org.scalikejdbc" %% "scalikejdbc-config" % "3.2.0",
    "org.postgresql" % "postgresql" % "42.2.1",

    "org.json4s" %% "json4s-native" % "3.5.2",
    "org.json4s" %% "json4s-ext" % "3.5.2",
    "org.json4s" %% "json4s-jackson" % "3.5.2",
    "de.heikoseeberger" %% "akka-http-json4s" % "1.16.1",

    "org.scalatest" %% "scalatest" % "3.0.4" % "test"
  )
}

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)

dockerBaseImage := "anapsix/alpine-java"
maintainer in Docker := "Make IT <it@makeit.com>"
version in Docker := "latest"

