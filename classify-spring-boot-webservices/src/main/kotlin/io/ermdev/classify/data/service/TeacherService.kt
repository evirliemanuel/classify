package io.ermdev.classify.data.service

import io.ermdev.classify.data.entity.Lesson
import io.ermdev.classify.data.entity.Teacher
import io.ermdev.classify.data.entity.User
import io.ermdev.classify.data.repository.TeacherRepository
import io.ermdev.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TeacherService(@Autowired private var teacherRepository: TeacherRepository) {

    fun findAll(): List<Teacher> = teacherRepository.findAll()

    fun findLessons(id: Long): List<Lesson> = teacherRepository.findLessons(teacherId = id)

    fun findById(id: Long): Teacher {
        return teacherRepository.findById(id)
                .orElseThrow { EntityException("No teacher entity with id $id exists!") }
    }

    fun findUser(id: Long): User {
        return teacherRepository.findUser(teacherId = id)
                ?: throw EntityException("No user entity found!")
    }

    fun findLesson(teacherId: Long, lessonId: Long): Lesson {
        return teacherRepository.findLesson(teacherId = teacherId, lessonId = lessonId)
                ?: throw EntityException("No lesson entity found!")
    }

    fun save(teacher: Teacher) {
        teacher.user = User(0, teacher.email.split("@")[0].toLowerCase(), "123")
        teacherRepository.save(teacher)
    }

    fun deleteById(id: Long) = teacherRepository.deleteById(id)
}