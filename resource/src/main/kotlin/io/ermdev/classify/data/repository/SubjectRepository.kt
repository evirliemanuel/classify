package io.ermdev.classify.data.repository

import io.ermdev.classify.data.entity.Subject
import org.springframework.data.jpa.repository.JpaRepository

interface SubjectRepository : JpaRepository<Subject, Long>