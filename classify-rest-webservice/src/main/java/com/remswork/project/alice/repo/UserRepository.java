package com.remswork.project.alice.repo;

import com.remswork.project.alice.model.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserDetail, Long> {

//    @Query("from UserDetail where username=:username LIMIT 1")
//    UserDetail getByUsername(@Param("username") String username);
}
