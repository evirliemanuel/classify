package io.ermdev.classify.data.service

import io.ermdev.classify.data.entity.*
import io.ermdev.classify.data.repository.LessonRepository
import io.ermdev.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.sql.DataSource

@Service
class LessonService(@Autowired private val lessonRepository: LessonRepository,
                    @Autowired private val dataSource: DataSource) {

    fun findAll(): List<Lesson> = lessonRepository.findAll()

    fun findById(id: Long): Lesson {
        return lessonRepository.findById(id)
                .orElseThrow { EntityException("No lesson with id $id exists!") }
    }

    fun findTeacher(id: Long): Teacher {
        return lessonRepository.findTeacher(id)
                ?: throw EntityException("No teacher entity found!")
    }

    fun findSubject(id: Long): Subject {
        return lessonRepository.findSubject(id)
                ?: throw EntityException("No subject entity found!")
    }

    fun findStudents(id: Long): List<Student> = lessonRepository.findStudents(id)

    fun findStudent(lessonId: Long, studentId: Long): Student {
        return lessonRepository.findStudent(lessonId, studentId)
                ?: throw EntityException("No student entity found!")
    }

    fun findSchedules(id: Long): List<Schedule> = lessonRepository.findSchedules(id)

    fun findSchedule(lessonId: Long, scheduleId: Long): Schedule {
        return lessonRepository.findSchedule(lessonId, scheduleId)
                ?: throw EntityException("No schedule entity found!")
    }

    fun save(lesson: Lesson) {
        lessonRepository.save(lesson)
    }

    fun deleteById(id: Long) = lessonRepository.deleteById(id)

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

    @Transactional
    fun deleteSchedule(lessonId: Long, scheduleId: Long) {
        lessonRepository.deleteSchedule(lessonId, scheduleId)
    }
}