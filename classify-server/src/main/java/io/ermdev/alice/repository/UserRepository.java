package io.ermdev.alice.repository;

import io.ermdev.alice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("from User where id=:userId")
    User findById(@Param("userId") Long userId);

    @Query("from User")
    List<User> findAll();
}
