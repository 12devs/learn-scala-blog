package com.cgbc.dashboard.domain


import java.util.UUID

import scalikejdbc._

case class NewComment(
                       mainText: String
                     ) {
  def toComment = Comment(
    commentId = CommentId(UUID.randomUUID().toString),
    mainText = mainText
  )
}


case class Comment(
                    commentId: CommentId,
                    mainText: String
                  )

object Comment extends SQLSyntaxSupport[Comment] {
  override val tableName = "comment"

  def apply(a: ResultName[Comment])(rs: WrappedResultSet): Comment = {
    new Comment(
      commentId = CommentId(rs.string(a.commentId)),
      mainText = rs.string(a.mainText)
    )
  }

  def getValues(comment: Comment): Map[SQLSyntax, ParameterBinder] = Map(
    Comment.column.commentId -> comment.commentId.id,
    Comment.column.mainText -> comment.mainText
  )

}
