package io.ermdev.classify.data.service

import io.ermdev.classify.data.entity.Teacher
import io.ermdev.classify.data.repository.TeacherRepository
import io.ermdev.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TeacherService(@Autowired var teacherRepository: TeacherRepository) {

    fun findById(teacherId: Long): Teacher {
        val teacher: Teacher? = teacherRepository.findById(teacherId)
        return teacher ?: throw EntityException("No teacher found")
    }

    fun findAll(): List<Teacher> {
        val teachers: List<Teacher>? = teacherRepository.findAll()
        return teachers ?: throw EntityException("No teacher found")
    }

    fun save(teacher: Teacher) = teacherRepository.save(teacher)

    fun delete(teacher: Teacher) = teacherRepository.delete(teacher)

    fun delete(teacherId: Long) = teacherRepository.delete(teacherId)
}