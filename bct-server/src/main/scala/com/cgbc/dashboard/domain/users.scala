package com.cgbc.dashboard.domain

import java.util.UUID

import scalikejdbc._

case class NewUser(
                   firstName: String,
                   lastName: String,
                   userLevel: UserLevel,
                   clientCn: String
                 ) {
  def toUser = User(
    userId = UserId(UUID.randomUUID().toString),
    firstName = firstName,
    lastName = lastName,
    userLevel = userLevel,
    clientCn = clientCn
  )
}


case class User(
                userId: UserId,
                firstName: String,
                lastName: String,
                userLevel: UserLevel,
                clientCn: String
              )

object User extends SQLSyntaxSupport[User] {
  override val tableName = "users"

  def apply(a: ResultName[User])(rs: WrappedResultSet): User = {
    new User(
      userId = UserId(rs.string(a.userId)),
      firstName = rs.string(a.firstName),
      lastName = rs.string(a.lastName),
      userLevel = UserLevel.fromText(rs.string(a.userLevel)),
      clientCn = rs.string(a.clientCn)
    )
  }

  def getValues(user: User): Map[SQLSyntax, ParameterBinder] = Map(
    User.column.userId -> user.userId.id,
    User.column.firstName -> user.firstName,
    User.column.lastName -> user.lastName,
    User.column.userLevel -> user.userLevel.asText,
    User.column.clientCn -> user.clientCn
  )

}
