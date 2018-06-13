package com.cgbc.dashboard.services

import com.cgbc.dashboard.domain.{AppId, UserApp, UserId}
import monix.eval.Task
import scalikejdbc._

class UserAppService {

  val ua: QuerySQLSyntaxProvider[SQLSyntaxSupport[UserApp], UserApp] = UserApp.syntax("ua")

  def getUserApps(userId: UserId): Task[List[UserApp]] = Task.now(
    DB readOnly { implicit session =>
      withSQL {
        select.from(UserApp as ua).where.eq(ua.userId, userId.id).orderBy(ua.appId)
      }.map(UserApp(ua.resultName)).list.apply()
    }
  )

  def getUserApps(appId: AppId): Task[List[UserApp]] = Task.now(
    DB readOnly { implicit session =>
      withSQL {
        select.from(UserApp as ua).where.eq(ua.appId, appId.id).orderBy(ua.userId)
      }.map(UserApp(ua.resultName)).list.apply()
    }
  )

  def createUserApp(userApp: UserApp): Task[UserApp] = Task.now {
    DB autoCommit { implicit session =>
      withSQL {
        insert.into(UserApp).namedValues(UserApp.getValues(userApp))
      }.update().apply()
    }

    userApp
  }

  def updateUserApp(userApp: UserApp): Task[UserApp] = Task.now {
    DB autoCommit { implicit session =>
      withSQL {
        update(UserApp as ua).set(UserApp.getValues(userApp)).where.eq(ua.userId, userApp.userId.id).and.eq(ua.appId, userApp.appId.id)
      }.update().apply()
    }

    userApp
  }

  def deleteUserApp(userId: UserId, appId: AppId): Task[Unit] = Task.now {
    DB autoCommit { implicit session =>
      withSQL {
        delete.from(UserApp as ua).where.eq(ua.userId, userId.id).and.eq(ua.appId, appId.id)
      }.update().apply()
    }
  }

}
