package com.cgbc.dashboard.domain

import java.util.UUID

import scalikejdbc._

case class NewArticle(
                       title: String,
                       mainText: String
                     ) {
  def toArticle = Article(
    articleId = ArticleId(UUID.randomUUID().toString),
    title = title,
    mainText = mainText
  )
}


case class Article(
                    articleId: ArticleId,
                    title: String,
                    mainText: String
                  )

object Article extends SQLSyntaxSupport[Article] {
  override val tableName = "article"

  def apply(a: ResultName[Article])(rs: WrappedResultSet): Article = {
    new Article(
      articleId = ArticleId(rs.string(a.articleId)),
      title = rs.string(a.title),
      mainText = rs.string(a.mainText)
    )
  }

  def getValues(article: Article): Map[SQLSyntax, ParameterBinder] = Map(
    Article.column.articleId -> article.articleId.id,
    Article.column.title -> article.title,
    Article.column.mainText -> article.mainText
  )

}
