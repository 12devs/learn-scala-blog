package com.cgbc.dashboard.utils.json

import com.cgbc.dashboard.domain._
import org.json4s.CustomSerializer
import org.json4s.JsonAST.JString

object TypeSerializers {

  val all = List(
    new UserLevelSerializer
  )

  class UserLevelSerializer extends CustomSerializer[UserLevel](_ => ( {
    case JString(s) => UserLevel.fromText(s)
  }, {
    case ul: UserLevel => JString(ul.asText)
  }))

}
