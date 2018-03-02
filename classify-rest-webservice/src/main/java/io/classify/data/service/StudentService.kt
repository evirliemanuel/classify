package io.classify.data.service

import io.classify.data.entity.Student
import io.classify.data.entity.User
import io.classify.data.repository.StudentRepository
import io.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StudentService(@Autowired var studentRepository: StudentRepository) {

    fun findById(id: Long): Student {
        val student: Student? = studentRepository.findById(id)
        return student ?: throw EntityException("No student found")
    }

    fun findAll(): List<Student> {
        val students: List<Student>? = studentRepository.findAll()
        return students ?: throw EntityException("No student found")
    }

    fun findUser(id: Long): User {
        val user: User? = studentRepository.findUser(id)
        return user ?: throw EntityException("No student found")
    }

    fun save(student: Student) = studentRepository.save(student)

    fun delete(student: Student) = studentRepository.delete(student)

    fun delete(id: Long) = studentRepository.delete(id)
}