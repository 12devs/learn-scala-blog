package com.cgbc.dashboard.services

import com.cgbc.dashboard.domain._
import monix.eval.Task
import scalikejdbc._

class CommentService {

  val c: QuerySQLSyntaxProvider[SQLSyntaxSupport[Comment], Comment] = Comment.syntax("c")

  def getComment: Task[List[Comment]] = Task.now(
    DB readOnly { implicit session =>
      withSQL {
        select.from(Comment as c).orderBy(c.mainText)
      }.map(Comment(c.resultName)).list.apply()
    }
  )

  def createComment(newComment: NewComment): Task[Comment] = Task.now {
    val comment = newComment.toComment

    DB autoCommit { implicit session =>
      withSQL {
        insert.into(Comment).namedValues(Comment.getValues(comment))
      }.update().apply()
    }

    comment
  }

  def updateComment(comment: Comment): Task[Comment] = Task.now {
    DB autoCommit { implicit session =>
      withSQL {
        update(Comment as c).set(Comment.getValues(comment)).where.eq(c.commentId, comment.commentId.id)
      }.update().apply()
    }

    comment
  }

  def deleteComment(commentId: CommentId): Task[Unit] = Task.now {
    DB autoCommit { implicit session =>
      withSQL {
        delete.from(Comment as c).where.eq(c.commentId, commentId.id)
      }.update().apply()
    }
  }

}
