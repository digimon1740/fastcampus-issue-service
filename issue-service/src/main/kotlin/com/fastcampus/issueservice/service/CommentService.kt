package com.fastcampus.issueservice.service

import com.fastcampus.issueservice.domain.Comment
import com.fastcampus.issueservice.domain.CommentRepository
import com.fastcampus.issueservice.domain.IssueRepository
import com.fastcampus.issueservice.exception.NotFoundException
import com.fastcampus.issueservice.model.CommentRequest
import com.fastcampus.issueservice.model.CommentResponse
import com.fastcampus.issueservice.model.toResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val issueRepository: IssueRepository,
) {

    @Transactional
    fun create(issueId: Long, userId: Long, username:String, request: CommentRequest) : CommentResponse {
        val issue = issueRepository.findByIdOrNull(issueId) ?: throw NotFoundException("이슈가 존재하지 않습니다")

        val comment = Comment(
            issue = issue,
            userId = userId,
            username = username,
            body = request.body,
        )

        issue.comments.add(comment)
        return commentRepository.save(comment).toResponse()
    }

    @Transactional
    fun edit(id: Long, userId: Long, request: CommentRequest): CommentResponse? {
        return commentRepository.findByIdAndUserId(id, userId)?.run {
            body = request.body
            commentRepository.save(this).toResponse()
        }
    }

    @Transactional
    fun delete(issueId: Long, id: Long, userId: Long) {
        val issue = issueRepository.findByIdOrNull(issueId) ?: throw NotFoundException("이슈가 존재하지 않습니다")
        commentRepository.findByIdAndUserId(id, userId)?.let { comment->
            issue.comments.remove(comment)
        }
    }

}