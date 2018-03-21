package io.ermdev.classify.data.repository

import io.ermdev.classify.data.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface UserRepository : JpaRepository<User, Long> {

    @Query("from User where username=:username")
    fun findByUsername(@Param("username") username: String): Optional<User>
}