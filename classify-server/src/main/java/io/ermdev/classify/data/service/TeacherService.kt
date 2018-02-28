package io.ermdev.classify.data.service

import io.ermdev.classify.data.entity.Teacher
import io.ermdev.classify.data.repository.TeacherRepository
import io.ermdev.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TeacherService(@Autowired var teacherRepository: TeacherRepository) {

    fun findById(teacherId: Long): Teacher {
        val teacher = teacherRepository.findById(teacherId)
        if (teacher != null) {
            return teacher
        } else {
            throw EntityException("No teacher found")
        }
    }

    fun findAll(): List<Teacher> {
        val teachers = teacherRepository.findAll()
        if (teachers != null) {
            return teachers
        } else {
            throw EntityException("No teacher found")
        }
    }
}