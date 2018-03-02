package io.classify.data.repository

import io.classify.data.entity.Teacher
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface TeacherRepository : JpaRepository<Teacher, Long> {

    @Query("from Teacher where id=:teacherId")
    fun findById(@Param("teacherId") teacherId: Long): Teacher

    @Query("from Teacher")
    override fun findAll(): List<Teacher>
}