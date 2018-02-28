package io.ermdev.alice.repository;

import io.ermdev.alice.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("from Student where id=:studentId")
    Student findById(@Param("studentId") Long studentId);
}
