package io.ermdev.alice.repository;

import io.ermdev.alice.entity.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TermRepository extends JpaRepository<Term, Long> {

    @Query("from Term where id=:termId")
    Term findById(@Param("termId") Long termId);
}
