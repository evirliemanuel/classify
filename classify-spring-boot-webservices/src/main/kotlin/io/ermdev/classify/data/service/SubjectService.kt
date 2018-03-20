package io.ermdev.classify.data.service

import io.ermdev.classify.data.entity.Subject
import io.ermdev.classify.data.repository.SubjectRepository
import io.ermdev.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SubjectService(@Autowired var subjectRepository: SubjectRepository) {

    fun findById(id: Long): Subject {
        val subject: Subject? = subjectRepository.findById(id).orElse(Subject())
        return subject ?: throw EntityException("No subject found")
    }

    fun findAll(): List<Subject> {
        val subjects: List<Subject>? = subjectRepository.findAll()
        return subjects ?: throw EntityException("No subject found")
    }

    fun save(subject: Subject) = subjectRepository.save(subject)

    fun delete(subject: Subject) = subjectRepository.delete(subject)

    fun delete(id: Long) = subjectRepository.deleteById(id)
}