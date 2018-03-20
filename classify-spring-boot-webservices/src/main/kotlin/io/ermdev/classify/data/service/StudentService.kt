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

    fun findById(id: Long): Student {
        val student: Student? = studentRepository.findById(id).orElse(Student())
        return student ?: throw EntityException("No student found")
    }

    fun findAll(): List<Student> {
        val students: List<Student>? = studentRepository.findAll()
        return students ?: throw EntityException("No student found")
    }

    fun findUser(id: Long): User {
        val user: User? = studentRepository.findUser(id)
        return user ?: throw EntityException("No user found")
    }

    fun findSubjects(id: Long): List<Subject> {
        val subjects: List<Subject>? = studentRepository.findSubjects(id)
        return subjects ?: throw EntityException("No subject found")
    }

    fun findSubject(studentId: Long, subjectId: Long): Subject {
        val subject: Subject? = studentRepository.findSubject(studentId, subjectId)
        return subject ?: throw EntityException("No subject found")
    }

    fun findTeachers(id: Long): List<Teacher> {
        val teachers: List<Teacher>? = studentRepository.findTeachers(id)
        return teachers ?: throw EntityException("No teacher found")
    }
}