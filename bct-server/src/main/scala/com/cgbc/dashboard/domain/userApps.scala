package com.cgbc.dashboard.domain

import scalikejdbc._

case class UserApp(
                    userId: UserId,
                    appId: AppId,
                    data: String
                  )

object UserApp extends SQLSyntaxSupport[UserApp] {
  override val tableName = "user_apps"

  def apply(a: ResultName[UserApp])(rs: WrappedResultSet): UserApp = {
    new UserApp(
      userId = UserId(rs.string(a.userId)),
      appId = AppId(rs.string(a.appId)),
      data = rs.string(a.data)
    )
  }

  def getValues(userApp: UserApp): Map[SQLSyntax, ParameterBinder] = Map(
    UserApp.column.userId -> userApp.userId.id,
    UserApp.column.appId -> userApp.appId.id,
    UserApp.column.data -> userApp.data
  )

}
