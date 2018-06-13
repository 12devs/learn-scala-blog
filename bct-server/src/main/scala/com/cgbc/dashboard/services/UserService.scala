package com.cgbc.dashboard.services

import com.cgbc.dashboard.domain._
import monix.eval.Task
import scalikejdbc._

class UserService {

  val u: QuerySQLSyntaxProvider[SQLSyntaxSupport[User], User] = User.syntax("u")
  val ua: QuerySQLSyntaxProvider[SQLSyntaxSupport[UserApp], UserApp] = UserApp.syntax("ua")

  def getUsers: Task[List[User]] = Task.now(
    DB readOnly { implicit session =>
      withSQL {
        select.from(User as u).orderBy(u.firstName, u.lastName)
      }.map(User(u.resultName)).list.apply()
    }
  )

  def createUser(newUser: NewUser): Task[User] = Task.now {
    val user = newUser.toUser

    DB autoCommit { implicit session =>
      withSQL {
        insert.into(User).namedValues(User.getValues(user))
      }.update().apply()
    }

    user
  }

  def updateUser(user: User): Task[User] = Task.now {
    DB autoCommit { implicit session =>
      withSQL {
        update(User as u).set(User.getValues(user)).where.eq(u.userId, user.userId.id)
      }.update().apply()
    }

    user
  }

  def getUser(clientCn: String): Task[Option[User]] = Task.now {
    DB autoCommit { implicit session =>
      withSQL {
        select.from(User as u).where.eq(u.clientCn, clientCn)
      }.map(User(u.resultName)).headOption().apply()
    }
  }

  def getOrCreateUser(clientCn: String): Task[(User, Boolean)] = {
    getUser(clientCn).flatMap {
      case Some(user) => Task.now((user, false))
      case None =>
        createUser(NewUser(clientCn, "", StandardUserLevel, clientCn))
          .map(user => (user, true))
    }
  }

  def deleteUser(userId: UserId): Task[Unit] = Task.now {
    DB autoCommit { implicit session =>
      withSQL {
        delete.from(UserApp as ua).where.eq(ua.userId, userId.id)
      }.update().apply()

      withSQL {
        delete.from(User as u).where.eq(u.userId, userId.id)
      }.update().apply()
    }
  }

}
