package com.cgbc.dashboard.services

import com.cgbc.dashboard.domain.{AppId, Component, ComponentId, NewComponent}
import monix.eval.Task
import scalikejdbc._
import sqls.distinct

class ComponentService {

  val c: QuerySQLSyntaxProvider[SQLSyntaxSupport[Component], Component] = Component.syntax("a")

  def getComponents(): Task[List[Component]] = Task.now(
    DB readOnly { implicit session =>
      withSQL {
        select.from(Component as c).orderBy(c.componentName)
      }.map(Component(c.resultName)).list.apply()
    }
  )

  def getComponentsForApp(appId: AppId): Task[List[Component]] = Task.now(
    DB readOnly { implicit session =>
      withSQL {
        select.from(Component as c).where.eq(c.appId, appId.id).orderBy(c.componentName)
      }.map(Component(c.resultName)).list.apply()
    }
  )

  def getAppsWithComponents(): Task[List[String]] = Task.now(
    DB readOnly { implicit session =>
      withSQL {
        select(distinct(c.appId)).from(Component as c).groupBy(c.appId)
      }.map(rs => rs.string(1)).list.apply()
    }
  )

  def createComponent(newComponent: NewComponent): Task[Component] = Task.now {
    val component = newComponent.toComponent

    DB autoCommit { implicit session =>
      withSQL {
        insert.into(Component).namedValues(Component.getValues(component))
      }.update().apply()
    }

    component
  }

  def updateComponent(component: Component): Task[Component] = Task.now {
    DB autoCommit { implicit session =>
      withSQL {
        update(Component as c).set(Component.getValues(component)).where.eq(c.componentId, component.componentId.id)
      }.update().apply()
    }

    component
  }

  def deleteComponent(componentId: ComponentId): Task[Unit] = Task.now {
    DB autoCommit { implicit session =>
      withSQL {
        delete.from(Component as c).where.eq(c.componentId, componentId.id)
      }.update().apply()
    }
  }

}
