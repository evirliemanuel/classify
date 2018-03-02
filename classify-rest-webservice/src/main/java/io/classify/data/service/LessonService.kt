package io.classify.data.service

import io.classify.data.entity.Lesson
import io.classify.data.entity.Student
import io.classify.data.entity.Subject
import io.classify.data.entity.Teacher
import io.classify.data.repository.LessonRepository
import io.classify.exception.EntityException
import org.springframework.stereotype.Service

@Service
class LessonService(val lessonRepository: LessonRepository) {

    fun findById(id: Long): Lesson {
        val lesson: Lesson? = lessonRepository.findById(id)
        return lesson ?: throw EntityException("No lesson found")
    }

    fun findAll(): List<Lesson> {
        val lessons: List<Lesson>? = lessonRepository.findAll()
        return lessons ?: throw EntityException("No lesson found")
    }

    fun findTeacher(id: Long): Teacher {
        val teacher: Teacher? = lessonRepository.findTeacher(id)
        return teacher ?: throw EntityException("No teacher found")
    }

    fun findSubject(id: Long): Subject {
        val subject: Subject? = lessonRepository.findSubject(id)
        return subject ?: throw EntityException("No subject found")
    }

    fun findStudentById(lessonId: Long, studentId: Long): Student {
        val student: Student? = lessonRepository.findStudentById(lessonId, studentId)
        return student ?: throw EntityException("No student found")
    }

    fun findStudents(id: Long): List<Student> {
        val students: List<Student>? = lessonRepository.findStudents(id)
        return students ?: throw EntityException("No student found")
    }

    fun save(lesson: Lesson) = lessonRepository.save(lesson)

    fun delete(lesson: Lesson) = lessonRepository.delete(lesson)

    fun delete(id: Long) = lessonRepository.delete(id)
}