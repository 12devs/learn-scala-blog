package com.cgbc.dashboard.auth

import java.time.ZonedDateTime

import akka.http.scaladsl.server._
import akka.http.scaladsl.server.directives._
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.cgbc.dashboard.domain.{User, UserId, UserLevel}
import com.cgbc.dashboard.services.ConfigurationService
import com.typesafe.scalalogging.StrictLogging

import scala.util.Try

case class JwtClaims(
                      userId: UserId,
                      firstName: String,
                      lastName: String,
                      userLevel: UserLevel
                    )

class JwtTokenService extends Directives with StrictLogging {

  private val tokenPrefix = "Bearer "

  val configurationService = new ConfigurationService

  private val jwtKey = configurationService.jwtConfig.jwtKey
  private val verifier = JWT.require(Algorithm.HMAC512(jwtKey)).build()

  def claimsFromToken(token: String): Option[JwtClaims] = {
    Try(verifier.verify(token)).toOption match {
      case Some(decodedJWT) =>
        val userId = UserId(decodedJWT.getClaim("userId").asString())
        val firstName = decodedJWT.getClaim("firstName").asString()
        val lastName = decodedJWT.getClaim("lastName").asString()
        val userLevel = UserLevel.fromText(decodedJWT.getClaim("userLevel").asString())
        Some(JwtClaims(userId, firstName, lastName, userLevel))
      case None =>
        logger.warn(s"Failed to decode token - $token" + token)
        None
    }
  }

  def newToken(a: User): String = {
    val expiresAt: java.util.Date = java.util.Date.from(ZonedDateTime.now().plusHours(8).toInstant)

    JWT.create()
      .withClaim("userId", a.userId.id)
      .withClaim("firstName", a.firstName)
      .withClaim("lastName", a.lastName)
      .withClaim("userLevel", a.userLevel.asText)
//      .withExpiresAt(expiresAt)
      .sign(Algorithm.HMAC512(jwtKey))
  }

  def auth(requiredUserLevel: Option[UserLevel] = None): Directive1[JwtClaims] = {

    optionalHeaderValueByName("Authorization").flatMap {
      case Some(token) if token.startsWith(tokenPrefix) =>
        claimsFromToken(token.substring(tokenPrefix.length)) match {
          case Some(claims) =>
            requiredUserLevel match {
              case Some(level) if claims.userLevel == level => provide(claims)
              case None => provide(claims)
              case _ => reject(AuthorizationFailedRejection)
            }
          case _ => reject(AuthorizationFailedRejection)
        }
      case Some(_) =>
        println("Incorrect Authorization header sent.")
        reject(AuthorizationFailedRejection)
      case None =>
        //        println("Route requires Authorization header, but it was not sent.")
        reject(AuthorizationFailedRejection)
    }
  }

}
