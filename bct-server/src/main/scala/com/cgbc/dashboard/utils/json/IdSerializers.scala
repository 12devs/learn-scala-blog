package com.cgbc.dashboard.utils.json

import com.cgbc.dashboard.domain._
import org.json4s.CustomSerializer
import org.json4s.JsonAST.JString

object IdSerializers {

  val all = List(
    new AppIdSerializer,
    new ComponentIdSerializer,
    new DefaultTemplateIdSerializer,
    new NotificationIdSerializer,
    new UserIdSerializer,
    new UserTemplateIdSerializer
  )

  class AppIdSerializer extends CustomSerializer[AppId](_ => ( { case JString(id) => AppId(id)}, { case AppId(id) => JString(id)}))
  class ComponentIdSerializer extends CustomSerializer[ComponentId](_ => ( { case JString(id) => ComponentId(id)}, { case ComponentId(id) => JString(id)}))
  class DefaultTemplateIdSerializer extends CustomSerializer[DefaultTemplateId](_ => ( { case JString(id) => DefaultTemplateId(id)}, { case DefaultTemplateId(id) => JString(id)}))
  class NotificationIdSerializer extends CustomSerializer[NotificationId](_ => ( { case JString(id) => NotificationId(id)}, { case NotificationId(id) => JString(id)}))
  class UserIdSerializer extends CustomSerializer[UserId](_ => ( { case JString(id) => UserId(id)}, { case UserId(id) => JString(id)}))
  class UserTemplateIdSerializer extends CustomSerializer[UserTemplateId](_ => ( { case JString(id) => UserTemplateId(id)}, { case UserTemplateId(id) => JString(id)}))

}
