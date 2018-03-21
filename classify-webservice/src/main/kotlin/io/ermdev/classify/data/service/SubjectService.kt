package io.ermdev.classify.data.service

import io.ermdev.classify.data.entity.Subject
import io.ermdev.classify.data.repository.SubjectRepository
import io.ermdev.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SubjectService(@Autowired var subjectRepository: SubjectRepository) {

    fun findAll(): List<Subject> = subjectRepository.findAll()

    fun findById(id: Long): Subject {
        return subjectRepository.findById(id)
                .orElseThrow { EntityException("No subject found") }
    }
}