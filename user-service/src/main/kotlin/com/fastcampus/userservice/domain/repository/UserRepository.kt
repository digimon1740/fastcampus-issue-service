package com.fastcampus.userservice.domain.repository

import com.fastcampus.userservice.domain.entity.User
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface UserRepository : CoroutineCrudRepository<User, Long> {

    suspend fun findByEmail(email: String): User?
}