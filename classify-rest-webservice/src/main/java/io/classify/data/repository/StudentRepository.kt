package io.classify.data.repository

import io.classify.data.entity.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface StudentRepository : JpaRepository<Student, Long> {

    @Query("from Student where id=:studentId")
    fun findById(@Param("studentId") studentId: Long): Student

    @Query("from Student")
    override fun findAll(): List<Student>
}