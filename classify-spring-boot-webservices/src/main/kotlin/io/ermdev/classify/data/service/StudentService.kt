package io.ermdev.classify.data.service

import io.ermdev.classify.data.entity.Lesson
import io.ermdev.classify.data.entity.Student
import io.ermdev.classify.data.entity.User
import io.ermdev.classify.data.repository.StudentRepository
import io.ermdev.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StudentService(@Autowired private val studentRepository: StudentRepository) {

    fun findAll(): List<Student> = studentRepository.findAll()

    fun findById(id: Long): Student {
        return studentRepository.findById(id)
                .orElseThrow { EntityException("No teacher student with id $id exists!") }
    }

    fun findUser(id: Long): User {
        return studentRepository.findUser(id)
                ?: throw EntityException("No user entity found!")
    }

    fun findLessons(id: Long): List<Lesson> = studentRepository.findLessons(studentId = id)

    fun findLesson(teacherId: Long, lessonId: Long): Lesson {
        return studentRepository.findLesson(studentId = teacherId, lessonId = lessonId)
                ?: throw EntityException("No lesson entity found!")
    }

    fun save(student: Student) {
        if (student.id == 0L) {
            student.user = User(username = student.name.toLowerCase(), password = "${student.number}")
        }
        studentRepository.save(student)
    }

    fun deleteById(id: Long) = studentRepository.deleteById(id)
}