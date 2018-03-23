package io.ermdev.classify.data.repository

import io.ermdev.classify.data.entity.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface LessonRepository : JpaRepository<Lesson, Long> {

    @Query("from Teacher as t join t.lessons as l where l.id=:lessonId")
    fun findTeacher(@Param("lessonId") lessonId: Long): Teacher?

    @Query("from Subject as s join s.lessons as l where l.id=:lessonId")
    fun findSubject(@Param("lessonId") lessonId: Long): Subject?

    @Query("from Student as a join a.lessons as b where b.id=:lessonId")
    fun findStudents(@Param("lessonId") lessonId: Long): List<Student>

    @Query("from Student as a join a.lessons as b where b.id=:lessonId and a.id=:studentId")
    fun findStudent(@Param("lessonId") lessonId: Long,
                    @Param("studentId") studentId: Long): Student?

    @Query("from Schedule as a join a.lesson as b where b.id=:lessonId")
    fun findSchedules(@Param("lessonId") lessonId: Long): List<Schedule>

    @Query("from Schedule as a join a.lesson as b where b.id=:lessonId and a.id=:scheduleId")
    fun findSchedule(@Param("lessonId") lessonId: Long,
                     @Param("scheduleId") scheduleId: Long): Schedule?

    @Query("delete from Schedule where lessonId=:lessonId and id=:scheduleId")
    fun deleteSchedule(@Param("lessonId") lessonId: Long,
                       @Param("scheduleId") scheduleId: Long)
}