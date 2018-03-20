package io.ermdev.classify.data.repository

import io.ermdev.classify.data.entity.Student
import io.ermdev.classify.data.entity.Subject
import io.ermdev.classify.data.entity.Teacher
import io.ermdev.classify.data.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface StudentRepository : JpaRepository<Student, Long> {

    @Query("from User as u join u.student as s where s.id=:studentId")
    fun findUser(@Param("studentId") studentId: Long): Optional<User>

    @Query("from Subject as a join a.lessons as b join b.students as c where c.id=:studentId")
    fun findSubjects(@Param("studentId") studentId: Long): List<Subject>

    @Query("from Subject as a join a.lessons as b join b.students as c where c.id=:studentId and a.id=:subjectId")
    fun findSubject(@Param("studentId") studentId: Long,
                    @Param("subjectId") subjectId: Long): Optional<Subject>

    @Query("from Teacher as a join a.lessons as b join b.students as c where c.id=:studentId group by a.id")
    fun findTeachers(@Param("studentId") studentId: Long): List<Teacher>

    @Transactional
    @Modifying
    @Query("update Student as s set s.user = null where s.id=:studentId")
    fun deleteUser(@Param("studentId") studentId: Long)
}