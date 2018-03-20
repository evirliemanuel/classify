package io.ermdev.classify.data.service

import io.ermdev.classify.data.entity.Student
import io.ermdev.classify.data.entity.Subject
import io.ermdev.classify.data.entity.Teacher
import io.ermdev.classify.data.entity.User
import io.ermdev.classify.data.repository.StudentRepository
import io.ermdev.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StudentService(@Autowired val studentRepository: StudentRepository) {

    fun findAll(): List<Student> = studentRepository.findAll()

    fun findSubjects(id: Long): List<Subject> = studentRepository.findSubjects(id)

    fun findTeachers(id: Long): List<Teacher> = studentRepository.findTeachers(id)

    fun findById(id: Long): Student {
        return studentRepository.findById(id)
                .orElseThrow { EntityException("No student found") }
    }

    fun findUser(id: Long): User {
        return studentRepository.findUser(id)
                .orElseThrow { EntityException("No user found") }
    }

    fun findSubject(studentId: Long, subjectId: Long): Subject {
        return studentRepository.findSubject(studentId, subjectId)
                .orElseThrow { EntityException("No subject found") }
    }
}