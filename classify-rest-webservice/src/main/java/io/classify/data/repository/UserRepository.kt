package io.classify.data.repository

import io.classify.data.entity.Student
import io.classify.data.entity.Teacher
import io.classify.data.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRepository : JpaRepository<User, Long> {

    @Query("from User where id=:userId")
    fun findById(@Param("userId") userId: Long): User

    @Query("from User where username=:username")
    fun findByUsername(@Param("username") username: String): User

    @Query("from Student as a join a.user as b where b.id=:userId")
    fun findStudent(@Param("userId") userId: Long): Student

    @Query("from Teacher as a join a.user as b where b.id=:userId")
    fun findTeacher(@Param("userId") userId: Long): Teacher
}