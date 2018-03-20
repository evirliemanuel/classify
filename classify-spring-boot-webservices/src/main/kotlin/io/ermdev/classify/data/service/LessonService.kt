package io.ermdev.classify.data.service

import io.ermdev.classify.data.entity.Lesson
import io.ermdev.classify.data.entity.Student
import io.ermdev.classify.data.entity.Subject
import io.ermdev.classify.data.entity.Teacher
import io.ermdev.classify.data.repository.LessonRepository
import io.ermdev.classify.data.repository.SubjectRepository
import io.ermdev.classify.data.repository.TeacherRepository
import io.ermdev.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.sql.DataSource

@Service
class LessonService(@Autowired val lessonRepository: LessonRepository,
                    @Autowired val subjectRepository: SubjectRepository,
                    @Autowired val teacherRepository: TeacherRepository,
                    @Autowired val dataSource: DataSource) {

    fun findAll(): List<Lesson> = lessonRepository.findAll()

    fun findStudents(id: Long): List<Student> = lessonRepository.findStudents(id)

    fun findById(id: Long): Lesson {
        return lessonRepository.findById(id)
                .orElseThrow { EntityException("No lesson found") }
    }

    fun findTeacher(id: Long): Teacher {
        return lessonRepository.findTeacher(id)
                .orElseThrow { EntityException("No teacher found") }

    }

    fun findSubject(id: Long): Subject {
        return lessonRepository.findSubject(id)
                .orElseThrow { EntityException("No subject found") }
    }

    fun findStudentById(lessonId: Long, studentId: Long): Student {
        return lessonRepository.findStudentById(lessonId, studentId)
                .orElseThrow { EntityException("No student found") }
    }

    fun save(lesson: Lesson, subjectId: Long, teacherId: Long) {
        lesson.subject = subjectRepository.findById(subjectId)
                .orElseThrow { EntityException("No subject found with id $subjectId!") }
        lesson.teacher = teacherRepository.findById(teacherId)
                .orElseThrow { EntityException("No teacher found with id $teacherId!") }
        lessonRepository.save(lesson)
    }

    fun save(lesson: Lesson) = lessonRepository.save(lesson)

    fun delete(lesson: Lesson) = lessonRepository.delete(lesson)

    fun delete(id: Long) = lessonRepository.deleteById(id)

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