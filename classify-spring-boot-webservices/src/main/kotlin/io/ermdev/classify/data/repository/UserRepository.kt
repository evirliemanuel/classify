package io.ermdev.classify.data.repository

import io.ermdev.classify.data.entity.Student
import io.ermdev.classify.data.entity.Teacher
import io.ermdev.classify.data.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface UserRepository : JpaRepository<User, Long> {

    @Query("from User where username=:username")
    fun findByUsername(@Param("username") username: String): Optional<User>

    @Query("from Student as a join a.user as b where b.id=:userId")
    fun findStudent(@Param("userId") userId: Long): Student?

    @Query("from Teacher as a join a.user as b where b.id=:userId")
    fun findTeacher(@Param("userId") userId: Long): Teacher?
}