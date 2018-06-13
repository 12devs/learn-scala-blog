package com.cgbc.dashboard.domain

import java.util.UUID

import scalikejdbc._

case class DefaultTemplate(
                            defaultTemplateId: DefaultTemplateId,
                            templateName: String,
                            json: String,
                            enabled: Boolean,
                            initialTemplate: Boolean
                          )

object DefaultTemplate extends SQLSyntaxSupport[DefaultTemplate] {
  override val tableName = "default_templates"

  def apply(dt: ResultName[DefaultTemplate])(rs: WrappedResultSet): DefaultTemplate = {
    new DefaultTemplate(
      defaultTemplateId = DefaultTemplateId(rs.string(dt.defaultTemplateId)),
      templateName = rs.string(dt.templateName),
      json = rs.string(dt.json),
      enabled = rs.boolean(dt.enabled),
      initialTemplate = rs.boolean(dt.initialTemplate)
    )
  }

  def getValues(dt: DefaultTemplate): Map[SQLSyntax, ParameterBinder] = Map(
    DefaultTemplate.column.defaultTemplateId -> dt.defaultTemplateId.id,
    DefaultTemplate.column.templateName -> dt.templateName,
    DefaultTemplate.column.json -> dt.json,
    DefaultTemplate.column.enabled -> dt.enabled,
    DefaultTemplate.column.initialTemplate -> dt.initialTemplate
  )
}

case class BasicDefaultTemplate(
                                 defaultTemplateId: DefaultTemplateId,
                                 templateName: String
                               )

object BasicDefaultTemplate extends SQLSyntaxSupport[BasicDefaultTemplate] {
  override val tableName = "default_template"
  def apply(rs: WrappedResultSet) = new BasicDefaultTemplate(
    defaultTemplateId = DefaultTemplateId(rs.string("default_template_id")),
    templateName = rs.string("template_name")
  )
}

case class NewDefaultTemplate(
                               templateName: String,
                               json: String,
                               enabled: Boolean,
                               initialTemplate: Boolean
                             ) {
  def toDefaultTemplate: DefaultTemplate = DefaultTemplate(
    defaultTemplateId = DefaultTemplateId(UUID.randomUUID().toString),
    templateName = templateName,
    json = json,
    enabled = enabled,
    initialTemplate = initialTemplate
  )
}

