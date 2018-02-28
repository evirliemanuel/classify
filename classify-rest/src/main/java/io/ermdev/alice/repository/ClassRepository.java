package io.ermdev.alice.repository;

import io.ermdev.alice.entity.Class;
import io.ermdev.alice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClassRepository extends JpaRepository<Class, Long> {

    @Query("from Class where id=:classId")
    Class findById(@Param("classId") Long classId);
}
