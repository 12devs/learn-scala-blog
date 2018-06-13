package com.cgbc.dashboard.domain

import scalikejdbc._

import java.util.UUID

case class UserTemplate(
                         userTemplateId: UserTemplateId,
                         userId: UserId,
                         templateName: String,
                         json: String,
                         basedOn: Option[DefaultTemplateId]
                       ) {
  def toBasicUserTemplate: BasicUserTemplate = BasicUserTemplate(
    userTemplateId = userTemplateId,
    userId = userId,
    templateName = templateName
  )
}


object UserTemplate extends SQLSyntaxSupport[UserTemplate] {
  override val tableName = "user_templates"

  def apply(ut: ResultName[UserTemplate])(rs: WrappedResultSet): UserTemplate = {
    new UserTemplate(
      userTemplateId = UserTemplateId(rs.string(ut.userTemplateId)),
      userId = UserId(rs.string(ut.userId)),
      templateName = rs.string(ut.templateName),
      json = rs.string(ut.json),
      basedOn = rs.stringOpt(ut.basedOn).map(DefaultTemplateId.apply)
    )

  }

  def getValues(ut: UserTemplate): Map[SQLSyntax, ParameterBinder] = Map(
    UserTemplate.column.userTemplateId -> ut.userTemplateId.id,
    UserTemplate.column.userId -> ut.userId.id,
    UserTemplate.column.templateName -> ut.templateName,
    UserTemplate.column.json -> ut.json,
    UserTemplate.column.basedOn -> ut.basedOn.map(_.id)
  )

}

object BasicUserTemplate extends SQLSyntaxSupport[BasicUserTemplate] {
  override val tableName = "user_templates"

  def apply(ut: ResultName[BasicUserTemplate])(rs: WrappedResultSet): BasicUserTemplate = {
    new BasicUserTemplate(
      userTemplateId = UserTemplateId(rs.string(ut.userTemplateId)),
      userId = UserId(rs.string(ut.userId)),
      templateName = rs.string(ut.templateName)
    )

  }

  def getValues(but: BasicUserTemplate): Map[SQLSyntax, ParameterBinder] = Map(
    UserTemplate.column.userTemplateId -> but.userTemplateId.id,
    UserTemplate.column.userId -> but.userId.id,
    UserTemplate.column.templateName -> but.templateName,
  )
}

case class UserTemplateUpdateRequest(
                                      userTemplateId: UserTemplateId,
                                      templateName: String,
                                      json: String,
                                      basedOn: Option[DefaultTemplateId]
                                    ) {
  def toUserTemplate(userId: UserId): UserTemplate = UserTemplate(
    userTemplateId = userTemplateId,
    userId = userId,
    templateName = templateName,
    json = json,
    basedOn = basedOn
  )
}

case class BasicUserTemplateUpdateRequest(
                                      userTemplateId: UserTemplateId,
                                      templateName: String,
                                    ) {
  def toBasicUserTemplate(userId: UserId): BasicUserTemplate = BasicUserTemplate(
    userTemplateId = userTemplateId,
    userId = userId,
    templateName = templateName,
  )
}

case class BasicUserTemplate(
                              userTemplateId: UserTemplateId,
                              userId: UserId,
                              templateName: String,
                            ) {
  def getValues(but: BasicUserTemplate): Map[SQLSyntax, ParameterBinder] = Map(
    UserTemplate.column.userTemplateId -> but.userTemplateId.id,
    UserTemplate.column.templateName -> but.templateName,
  )
}

case class NewUserTemplate(
                            templateName: String,
                            json: String,
                            basedOn: Option[DefaultTemplateId]
                          ) {
  def toUserTemplate(userId: UserId): UserTemplate = UserTemplate(
    userTemplateId = UserTemplateId(UUID.randomUUID().toString),
    userId = userId,
    templateName = templateName,
    json = json,
    basedOn = basedOn
  )
}
