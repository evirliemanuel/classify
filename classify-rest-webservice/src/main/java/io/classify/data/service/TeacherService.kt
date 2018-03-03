package io.classify.data.service

import io.classify.data.entity.Student
import io.classify.data.entity.Subject
import io.classify.data.entity.Teacher
import io.classify.data.entity.User
import io.classify.data.repository.TeacherRepository
import io.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TeacherService(@Autowired var teacherRepository: TeacherRepository) {

    fun findById(id: Long): Teacher {
        val teacher: Teacher? = teacherRepository.findById(id)
        return teacher ?: throw EntityException("No teacher found")
    }

    fun findAll(): List<Teacher> {
        val teachers: List<Teacher>? = teacherRepository.findAll()
        return teachers ?: throw EntityException("No teacher found")
    }

    fun findUser(id: Long): User {
        val user: User? = teacherRepository.findUser(id)
        return user ?: throw EntityException("No user found")
    }

    fun findSubjects(id: Long): List<Subject> {
        val subjects: List<Subject>? = teacherRepository.findSubjects(id)
        return subjects ?: throw EntityException("No subject found")
    }

    fun findSubject(teacherId: Long, subjectId: Long): Subject {
        val subject: Subject? = teacherRepository.findSubject(teacherId, subjectId)
        return subject ?: throw EntityException("No subject found")
    }

    fun findSubjectByLesson(teacherId: Long, lessonId: Long): Subject {
        val subject: Subject? = teacherRepository.findSubjectByLesson(teacherId, lessonId)
        return subject ?: throw EntityException("No subject found")
    }

    fun findStudents(teacherId: Long, lessonId: Long): List<Student> {
        val students: List<Student>? = teacherRepository.findStudents(teacherId, lessonId)
        return students ?: throw EntityException("No student found")
    }

    fun findStudent(teacherId: Long, lessonId: Long, studentId: Long): Student {
        val student: Student? = teacherRepository.findStudent(teacherId, lessonId, studentId)
        return student ?: throw EntityException("No student found")
    }
}