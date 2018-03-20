package io.ermdev.classify.data.repository

import io.ermdev.classify.data.entity.Subject
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SubjectRepository : JpaRepository<Subject, Long> {

    @Query("from Subject")
    override fun findAll(): List<Subject>
}