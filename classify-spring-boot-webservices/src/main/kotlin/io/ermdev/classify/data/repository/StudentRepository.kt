package io.ermdev.classify.data.repository

import io.ermdev.classify.data.entity.Lesson
import io.ermdev.classify.data.entity.Student
import io.ermdev.classify.data.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface StudentRepository : JpaRepository<Student, Long> {

    @Query("from User as u join u.student as s where s.id=:studentId")
    fun findUser(@Param("studentId") studentId: Long): User?

    @Query("from Lesson as a join a.students as b where b.id=:studentId")
    fun findLessons(@Param("studentId") studentId: Long): List<Lesson>

    @Query("from Lesson as a join a.students as b where b.id=:studentId and a.id=:lessonId")
    fun findLesson(@Param("studentId") studentId: Long,
                   @Param("lessonId") lessonId: Long): Lesson?
}