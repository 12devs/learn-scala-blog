package com.cgbc.dashboard.services

import com.typesafe.scalalogging.StrictLogging
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

case class JwtConfig(jwtKey: String)

class ConfigurationService extends StrictLogging {

  private val conf: Config = ConfigFactory.load
  private val jwtKey: String = conf.getString("jwtKey")

  val currentBuildNumber: Int = conf.getInt("currentBuildNumber")
  val jwtConfig = JwtConfig(jwtKey)

  logger.info(s"Application Version #$currentBuildNumber")
}
