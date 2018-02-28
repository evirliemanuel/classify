package io.ermdev.classify.data.repository;

import io.ermdev.classify.data.entity.User;
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
