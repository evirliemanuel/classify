package io.classify.data.repository

import io.classify.data.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRepository : JpaRepository<User, Long> {

    @Query("from User where id=:userId")
    fun findById(@Param("userId") userId: Long): User
}