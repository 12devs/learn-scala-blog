package com.cgbc.dashboard.services

import com.cgbc.dashboard.domain._
import monix.eval.Task
import scalikejdbc._
import sqls.{count, distinct}

class UserTemplateService {

  private val ut: QuerySQLSyntaxProvider[SQLSyntaxSupport[UserTemplate], UserTemplate] = UserTemplate.syntax("ut")
  private val but: QuerySQLSyntaxProvider[SQLSyntaxSupport[BasicUserTemplate], BasicUserTemplate] = BasicUserTemplate.syntax("but")

  def getUserTemplates(userId: UserId): Task[List[BasicUserTemplate]] = Task.now {
    val result = DB readOnly { implicit session =>
      withSQL {
        select.from(UserTemplate as ut).where.eq(ut.userId, userId.id).orderBy(ut.templateName)
      }.map(UserTemplate(ut.resultName)).list.apply().map(_.toBasicUserTemplate)
    }
    result.sortBy(_.templateName != "News & Analytics").sortBy(_.templateName != "Trade")
  }

  def getUserTemplate(userId: UserId, templateName: String): Task[UserTemplate] = {
    Task.now(
      DB readOnly { implicit session =>
        withSQL {
          select.from(UserTemplate as ut).where.eq(ut.templateName, templateName).and.eq(ut.userId, userId.id)
        }.map(UserTemplate(ut.resultName)).single().apply().get
      }
    )
  }

  def createUserTemplate(userId: UserId, newUt: NewUserTemplate): Task[UserTemplate] = Task.now {
    val template = newUt.toUserTemplate(userId)

    DB autoCommit { implicit session =>
      withSQL {
        insert.into(UserTemplate).namedValues(UserTemplate.getValues(template))
      }.update().apply()
    }

    template
  }

  def updateUserTemplate(template: UserTemplate): Task[UserTemplate] = Task.now {
    DB autoCommit { implicit session =>
      withSQL {
        update(UserTemplate as ut).set(UserTemplate.getValues(template)).where.eq(ut.userTemplateId, template.userTemplateId.id).and.eq(ut.userId, template.userId.id)
      }.update().apply()
    }

    template
  }

  def updateBasicUserTemplate(template: BasicUserTemplate): Task[BasicUserTemplate] = Task.now {
    DB autoCommit { implicit session =>
      withSQL {
        update(BasicUserTemplate as but).set(BasicUserTemplate.getValues(template)).where.eq(but.userTemplateId, template.userTemplateId.id).and.eq(but.userId, template.userId.id)
      }.update().apply()
    }

    template
  }

  def deleteUserTemplate(userId: UserId, userTemplateId: UserTemplateId): Task[Unit] = Task.now {
    DB autoCommit { implicit session =>
      val templateCount = withSQL {
        select(count(distinct(ut.userTemplateId))).from(UserTemplate as ut).where.eq(ut.userId, userId.id)
      }.map(_.int(1)).single.apply().get

      if (templateCount > 1) {
        withSQL {
          delete.from(UserTemplate as ut).where.eq(ut.userTemplateId, userTemplateId.id).and.eq(ut.userId, userId.id)
        }.update().apply()
      }
    }
  }


}
