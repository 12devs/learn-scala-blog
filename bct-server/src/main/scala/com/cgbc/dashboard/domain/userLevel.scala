package com.cgbc.dashboard.domain

sealed trait UserLevel {
  def asText: String
}

case object AdminUserLevel extends UserLevel {
  val asText = "USER_LEVEL_ADMIN"
}

case object StandardUserLevel extends UserLevel {
  val asText = "USER_LEVEL_STANDARD"
}

object UserLevel {
  def fromText(text: String): UserLevel = text match {
    case AdminUserLevel.asText => AdminUserLevel
    case StandardUserLevel.asText => StandardUserLevel
    case _ => throw new RuntimeException(s"Invalid user level: $text")
  }
}
