package com.cgbc.dashboard.domain

import java.util.UUID

import scalikejdbc._

case class NewComponent(
                         componentName: String,
                         appId: AppId,
                   url: String,
                   logo: String,
                   defaultInstanceConfiguration: String,
                   apiEnabled: Boolean,
                   enabled: Boolean
                 ) {
  def toComponent = Component(
    componentId = ComponentId(UUID.randomUUID().toString),
    componentName = componentName,
    appId = appId,
    url = url,
    logo = logo,
    defaultInstanceConfiguration = defaultInstanceConfiguration,
    apiEnabled = apiEnabled,
    enabled = enabled
  )
}


case class Component(
                componentId: ComponentId,
                componentName: String,
                appId: AppId,
                url: String,
                logo: String,
                defaultInstanceConfiguration: String,
                apiEnabled: Boolean,
                enabled: Boolean
              )

object Component extends SQLSyntaxSupport[Component] {
  override val tableName = "components"

  def apply(a: ResultName[Component])(rs: WrappedResultSet): Component = {
    new Component(
      componentId = ComponentId(rs.string(a.componentId)),
      componentName = rs.string(a.componentName),
      appId = AppId(rs.string(a.appId)),
      url = rs.string(a.url),
      logo = rs.string(a.logo),
      defaultInstanceConfiguration = rs.string(a.defaultInstanceConfiguration),
      apiEnabled = rs.boolean(a.apiEnabled),
      enabled = rs.boolean(a.enabled)
    )

  }

  def getValues(app: Component): Map[SQLSyntax, ParameterBinder] = Map(
    Component.column.componentId -> app.componentId.id,
    Component.column.componentName -> app.componentName,
    Component.column.appId -> app.appId.id,
    Component.column.url -> app.url,
    Component.column.logo -> app.logo,
    Component.column.defaultInstanceConfiguration -> app.defaultInstanceConfiguration,
    Component.column.apiEnabled -> app.apiEnabled,
    Component.column.enabled -> app.enabled
  )

}
