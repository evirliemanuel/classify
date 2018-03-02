package io.classify.data.repository

import io.classify.data.entity.Subject
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SubjectRepository : JpaRepository<Subject, Long> {

    @Query("from Subject where id=:subjectId")
    fun findById(@Param("subjectId") subjectId: Long): Subject

    @Query("from Subject")
    override fun findAll(): List<Subject>
}