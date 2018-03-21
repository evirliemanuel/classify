package io.ermdev.classify.data.service

import io.ermdev.classify.data.entity.Lesson
import io.ermdev.classify.data.entity.Teacher
import io.ermdev.classify.data.entity.User
import io.ermdev.classify.data.repository.TeacherRepository
import io.ermdev.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils

@Service
class TeacherService(@Autowired private val teacherRepository: TeacherRepository) {

    fun findAll(): List<Teacher> = teacherRepository.findAll()

    fun findById(id: Long): Teacher {
        return teacherRepository.findById(id)
                .orElseThrow { EntityException("No teacher entity with id $id exists!") }
    }

    fun findUser(id: Long): User {
        return teacherRepository.findUser(teacherId = id)
                ?: throw EntityException("No user entity found!")
    }

    fun findLessons(id: Long): List<Lesson> = teacherRepository.findLessons(teacherId = id)

    fun findLesson(teacherId: Long, lessonId: Long): Lesson {
        return teacherRepository.findLesson(teacherId = teacherId, lessonId = lessonId)
                ?: throw EntityException("No lesson entity found!")
    }

    fun save(teacher: Teacher) {
        val emailPattern = "^([0-9a-zA-Z]+([_.][0-9a-zA-Z]+)?){3,}@[0-9a-zA-Z]+\\.[0-9a-zA-Z]+$"
        if (StringUtils.isEmpty(teacher.name)) {
            throw EntityException("name cannot be empty")
        }
        if (!teacher.name.matches(Regex("^[0-9a-zA-Z]+$"))) {
            throw EntityException("name cannot contain special characters")
        }
        if (StringUtils.isEmpty(teacher.email)) {
            throw EntityException("email cannot be empty")
        }
        if (!teacher.email.matches(Regex(emailPattern))) {
            throw EntityException("email must be in valid format")
        }
        if (teacher.id == 0L) {
            teacher.user = User(username = teacher.email.split("@")[0].toLowerCase(), password = "123")
        }
        teacherRepository.save(teacher)
    }

    fun deleteById(id: Long) = teacherRepository.deleteById(id)
}