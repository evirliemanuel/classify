package io.classify.data.repository

import io.classify.data.entity.Lesson
import io.classify.data.entity.Student
import io.classify.data.entity.Subject
import io.classify.data.entity.Teacher
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface LessonRepository : JpaRepository<Lesson, Long> {

    @Query("from Lesson where id=:lessonId")
    fun findById(@Param("lessonId") lessonId: Long): Lesson

    @Query("from Teacher as t join t.lessons as l where l.id=:lessonId")
    fun findTeacher(@Param("lessonId") lessonId: Long): Teacher

    @Query("from Subject as s join s.lessons as l where l.id=:lessonId")
    fun findSubject(@Param("lessonId") lessonId: Long): Subject

    @Query("from Student as s join s.lessons as l where l.id=:lessonId and s.id=:studentId")
    fun findStudentById(@Param("lessonId") lessonId: Long,
                        @Param("studentId") studentId: Long): Student

    @Query("from Student as s join s.lessons as l where l.id=:lessonId")
    fun findStudents(@Param("lessonId") lessonId: Long): List<Student>
}