package io.ermdev.classify.data.service

import io.ermdev.classify.data.entity.Student
import io.ermdev.classify.data.entity.Subject
import io.ermdev.classify.data.entity.Teacher
import io.ermdev.classify.data.entity.User
import io.ermdev.classify.data.repository.TeacherRepository
import io.ermdev.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TeacherService(@Autowired var teacherRepository: TeacherRepository) {

    fun findAll(): List<Teacher> = teacherRepository.findAll()

    fun findSubjects(id: Long): List<Subject> = teacherRepository.findSubjects(id)

    fun findStudents(teacherId: Long, lessonId: Long):
            List<Student> = teacherRepository.findStudents(teacherId, lessonId)

    fun findById(id: Long): Teacher {
        return teacherRepository.findById(id)
                .orElseThrow { EntityException("No teacher found") }
    }

    fun findUser(id: Long): User {
        return teacherRepository.findUser(id)
                .orElseThrow { EntityException("No user found") }
    }

    fun findSubject(teacherId: Long, subjectId: Long): Subject {
        return teacherRepository.findSubject(teacherId, subjectId)
                .orElseThrow { EntityException("No subject found") }
    }

    fun findSubjectByLesson(teacherId: Long, lessonId: Long): Subject {
        return teacherRepository.findSubjectByLesson(teacherId, lessonId)
                .orElseThrow { EntityException("No subject found") }
    }

    fun findStudent(teacherId: Long, lessonId: Long, studentId: Long): Student {
        return teacherRepository.findStudent(teacherId, lessonId, studentId)
                .orElseThrow { EntityException("No student found") }
    }
}