package io.ermdev.classify.data.service

import io.ermdev.classify.data.entity.Teacher
import io.ermdev.classify.data.repository.TeacherRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TeacherService(@Autowired var teacherRepository: TeacherRepository) {

    fun findById(teacherId: Long): Teacher = teacherRepository.findById(teacherId)

    fun findAll(): List<Teacher> = teacherRepository.findAll()
}