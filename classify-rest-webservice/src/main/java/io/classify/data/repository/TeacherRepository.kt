package io.classify.data.repository

import io.classify.data.entity.Teacher
import io.classify.data.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface TeacherRepository : JpaRepository<Teacher, Long> {

    @Query("from Teacher where id=:teacherId")
    fun findById(@Param("teacherId") teacherId: Long): Teacher

    @Query("from User as u join u.teacher as t where t.id=:teacherId")
    fun findUser(@Param("teacherId") teacherId: Long): User

    @Transactional
    @Modifying
    @Query("update Teacher as t set t.user = null where t.id=:teacherId")
    fun deleteUser(@Param("teacherId") teacherId: Long)
}