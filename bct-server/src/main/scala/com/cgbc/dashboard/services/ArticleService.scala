package com.cgbc.dashboard.services

import com.cgbc.dashboard.domain._
import monix.eval.Task
import scalikejdbc._

class ArticleService {

  val a: QuerySQLSyntaxProvider[SQLSyntaxSupport[Article], Article] = Article.syntax("a")

  def getArticle: Task[List[Article]] = Task.now(
    DB readOnly { implicit session =>
      withSQL {
        select.from(Article as a).orderBy(a.title, a.mainText)
      }.map(Article(a.resultName)).list.apply()
    }
  )

  def createArticle(newArticle: NewArticle): Task[Article] = Task.now {
    val article = newArticle.toArticle

    DB autoCommit { implicit session =>
      withSQL {
        insert.into(Article).namedValues(Article.getValues(article))
      }.update().apply()
    }

    article
  }

  def updateArticle(article: Article): Task[Article] = Task.now {
    DB autoCommit { implicit session =>
      withSQL {
        update(Article as a).set(Article.getValues(article)).where.eq(a.articleId, article.articleId.id)
      }.update().apply()
    }

    article
  }

  def deleteArticle(articleId: ArticleId): Task[Unit] = Task.now {
    DB autoCommit { implicit session =>
      withSQL {
        delete.from(Article as a).where.eq(a.articleId, articleId.id)
      }.update().apply()
    }
  }

}
