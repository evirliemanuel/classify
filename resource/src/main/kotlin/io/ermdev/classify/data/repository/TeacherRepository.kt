package io.ermdev.classify.data.repository

import io.ermdev.classify.data.entity.Lesson
import io.ermdev.classify.data.entity.Teacher
import io.ermdev.classify.data.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface TeacherRepository : JpaRepository<Teacher, Long> {

    @Query("from Teacher where user.id=:userId")
    fun findByUserId(@Param("userId") userId: Long): Optional<Teacher>

    @Query("from Lesson as a join a.teacher as b where b.id=:teacherId")
    fun findLessons(@Param("teacherId") teacherId: Long): List<Lesson>

    @Query("from Lesson as a join a.teacher as b where b.id=:teacherId and a.id=:lessonId")
    fun findLesson(@Param("teacherId") teacherId: Long,
                   @Param("lessonId") lessonId: Long): Lesson?

    @Query("from User as u join u.teacher as t where t.id=:teacherId")
    fun findUser(@Param("teacherId") teacherId: Long): User?
}