package io.classify.data.repository

import io.classify.data.entity.Student
import io.classify.data.entity.Subject
import io.classify.data.entity.Teacher
import io.classify.data.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface TeacherRepository : JpaRepository<Teacher, Long> {

    @Query("from Teacher where id=:teacherId")
    fun findById(@Param("teacherId") teacherId: Long): Teacher

    @Query("from User as u join u.teacher as t where t.id=:teacherId")
    fun findUser(@Param("teacherId") teacherId: Long): User

    @Query("from Subject as a join a.lessons as b join b.teacher as c where c.id=:teacherId")
    fun findSubjects(@Param("teacherId") teacherId: Long): List<Subject>

    @Query("from Subject as a join a.lessons as b join b.teacher as c where c.id=:teacherId and a.id=:subjectId")
    fun findSubject(@Param("teacherId") teacherId: Long, @Param("subjectId") subjectId: Long): Subject

    @Query("from Subject as a join a.lessons as b join b.teacher as c where c.id=:teacherId and b.id=:lessonId")
    fun findSubjectByLesson(@Param("teacherId") teacherId: Long,
                            @Param("lessonId") lessonId: Long): Subject

    @Query("from Student as a join a.lessons as b join b.teacher as c where c.id=:teacherId and b.id=:lessonId")
    fun findStudents(@Param("teacherId") teacherId: Long, @Param("lessonId") lessonId: Long): List<Student>

    @Query("from Student as a join a.lessons as b join b.teacher as c where c.id=:teacherId and " +
            "b.id=:lessonId and a.id=:studentId")
    fun findStudent(@Param("teacherId") teacherId: Long,
                    @Param("lessonId") lessonId: Long,
                    @Param("studentId") studentId: Long): Student

    @Transactional
    @Modifying
    @Query("update Teacher as t set t.user = null where t.id=:teacherId")
    fun deleteUser(@Param("teacherId") teacherId: Long)
}