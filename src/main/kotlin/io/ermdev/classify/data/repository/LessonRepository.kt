package io.ermdev.classify.data.repository

import io.ermdev.classify.data.entity.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface LessonRepository : JpaRepository<Lesson, Long> {

    @Query("from Student as a join a.lessons as b where b.id=:lessonId")
    fun findStudents(@Param("lessonId") lessonId: Long): List<Student>

    @Query("from Student as a join a.lessons as b where b.id=:lessonId and a.id=:studentId")
    fun findStudent(@Param("lessonId") lessonId: Long,
                    @Param("studentId") studentId: Long): Student?

    @Query("from Schedule where lesson.id=:lessonId")
    fun findSchedules(@Param("lessonId") lessonId: Long): List<Schedule>

    @Query("from Schedule where lesson.id=:lessonId and id=:scheduleId")
    fun findSchedule(@Param("lessonId") lessonId: Long,
                     @Param("scheduleId") scheduleId: Long): Schedule?

    @Query("from Teacher as a join a.lessons as b where b.id=:lessonId")
    fun findTeacher(@Param("lessonId") lessonId: Long): Teacher?

    @Query("from Subject as a join a.lessons as b where b.id=:lessonId")
    fun findSubject(@Param("lessonId") lessonId: Long): Subject?

    @Modifying
    @Query("delete from Schedule where lesson.id=:lessonId and id=:scheduleId")
    fun deleteSchedule(@Param("lessonId") lessonId: Long,
                       @Param("scheduleId") scheduleId: Long)
}