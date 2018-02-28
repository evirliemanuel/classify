package io.ermdev.alice.repository;

import io.ermdev.alice.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query("from Subject where id=:subjectId")
    Subject findById(@Param("subjectId") Long subjectId);
}
