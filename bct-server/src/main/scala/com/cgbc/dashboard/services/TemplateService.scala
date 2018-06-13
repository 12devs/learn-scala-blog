package com.cgbc.dashboard.services

import java.util.UUID

import com.cgbc.dashboard.domain._
import monix.eval.Task
import scalikejdbc._

class TemplateService {

  private val ut: QuerySQLSyntaxProvider[SQLSyntaxSupport[UserTemplate], UserTemplate] = UserTemplate.syntax("ut")
  private val dt: QuerySQLSyntaxProvider[SQLSyntaxSupport[DefaultTemplate], DefaultTemplate] = DefaultTemplate.syntax("dt")

  def addDefaultTemplatesForUser(user: User): Task[User] = Task.now(
    DB autoCommit { implicit session =>
      val defaultTemplates = withSQL {
        select.from(DefaultTemplate as dt).where.eq(dt.initialTemplate, true).orderBy(dt.templateName)
      }.map(DefaultTemplate(dt.resultName)).list.apply()

      defaultTemplates map {
        template =>
          UserTemplate(
            userTemplateId = UserTemplateId(UUID.randomUUID().toString),
            userId = user.userId,
            templateName = template.templateName,
            json = template.json,
            basedOn = Some(template.defaultTemplateId)
          )
      } foreach {
        userTemplate =>
          withSQL {
            insert.into(UserTemplate).namedValues(UserTemplate.getValues(userTemplate))
          }.update().apply()
      }

      user
    }
  )

}
