package com.fastcampus.issueservice.domain

import com.fastcampus.issueservice.domain.enums.IssueStatus
import org.springframework.data.jpa.repository.JpaRepository

interface IssueRepository : JpaRepository<Issue, Long> {

    fun findAllByStatusOrderByCreatedAtDesc(status: IssueStatus) : List<Issue>?
}