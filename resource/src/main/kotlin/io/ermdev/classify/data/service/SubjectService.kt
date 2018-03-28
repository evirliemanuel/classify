package io.ermdev.classify.data.service

import io.ermdev.classify.data.entity.Subject
import io.ermdev.classify.data.repository.SubjectRepository
import io.ermdev.classify.exception.EntityException
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils

@Service
class SubjectService(private val subjectRepository: SubjectRepository) {

    fun findAll(): List<Subject> = subjectRepository.findAll()

    fun findById(id: Long): Subject {
        return subjectRepository.findById(id)
                .orElseThrow { EntityException("No subject entity with id $id exists!") }
    }

    fun save(subject: Subject) {
        if (StringUtils.isEmpty(subject.name)) {
            throw EntityException("name cannot be empty")
        }
        if (!subject.name.matches(Regex("^[a-zA-Z0-9]+( [a-zA-Z0-9]+)*$"))) {
            throw EntityException("name cannot contain special characters")
        }
        if (StringUtils.isEmpty(subject.code)) {
            throw EntityException("code cannot be empty")
        }
        if (!subject.code.matches(Regex("^[0-9a-zA-Z]+$"))) {
            throw EntityException("code cannot contain special characters")
        }
        subjectRepository.save(subject)
    }

    fun deleteById(id: Long) = subjectRepository.deleteById(id)
}