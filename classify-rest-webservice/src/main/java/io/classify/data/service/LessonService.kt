package io.classify.data.service

import io.classify.data.entity.Lesson
import io.classify.data.entity.Student
import io.classify.data.entity.Subject
import io.classify.data.entity.Teacher
import io.classify.data.repository.LessonRepository
import io.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.sql.DataSource

@Service
class LessonService(@Autowired val lessonRepository: LessonRepository,
                    @Autowired val dataSource: DataSource) {

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

    fun deleteStudent(lessonId: Long, studentId: Long) {
        try {
            val sql = "DELETE FROM tbl_student_lesson WHERE lesson_id = ? AND student_id = ?"
            val ps = dataSource.connection.prepareStatement(sql)
            ps.setLong(1, lessonId)
            ps.setLong(2, studentId)
            ps.executeUpdate()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}