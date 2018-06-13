package com.cgbc.dashboard.services

import com.cgbc.dashboard.domain._
import monix.eval.Task
import scalikejdbc._

class DefaultTemplateService {

  private val dt: QuerySQLSyntaxProvider[SQLSyntaxSupport[DefaultTemplate], DefaultTemplate] = DefaultTemplate.syntax("dt")

  def getDefaultTemplates: Task[List[DefaultTemplate]] = Task.now(
    DB readOnly { implicit session =>
      withSQL {
        select.from(DefaultTemplate as dt).orderBy(dt.templateName)
      }.map(DefaultTemplate(dt.resultName)).list.apply()
    }
  )

  def getDefaultTemplate(defaultTemplateId: DefaultTemplateId): Task[DefaultTemplate] = Task.now(
    DB readOnly { implicit session =>
      withSQL {
        select.from(DefaultTemplate as dt).where.eq(dt.defaultTemplateId, defaultTemplateId.id)
      }.map(DefaultTemplate(dt.resultName)).single().apply().get
    }
  )

  def getDefaultTemplate(templateName: String): Task[DefaultTemplate] = Task.now(
    DB readOnly { implicit session =>
      withSQL {
        select.from(DefaultTemplate as dt).where.eq(dt.templateName, templateName)
      }.map(DefaultTemplate(dt.resultName)).single().apply().get
    }
  )

  def createDefaultTemplate(newDt: NewDefaultTemplate): Task[DefaultTemplate] = Task.now {
    val template = newDt.toDefaultTemplate

    DB autoCommit { implicit session =>
      withSQL {
        insert.into(DefaultTemplate).namedValues(DefaultTemplate.getValues(template))
      }.update().apply()
    }

    template
  }

  def updateDefaultTemplate(template: DefaultTemplate): Task[DefaultTemplate] = Task.now {
    DB autoCommit { implicit session =>
      withSQL {
        update(DefaultTemplate as dt).set(DefaultTemplate.getValues(template)).where.eq(dt.defaultTemplateId, template.defaultTemplateId.id)
      }.update().apply()
    }

    template
  }

  def deleteDefaultTemplate(defaultTemplateId: DefaultTemplateId): Task[Unit] = Task.now {
    DB autoCommit { implicit session =>
      withSQL {
        delete.from(DefaultTemplate as dt).where.eq(dt.defaultTemplateId, defaultTemplateId.id).and.not.eq(dt.templateName, "Bottom")
      }.update().apply()
    }
  }

}
