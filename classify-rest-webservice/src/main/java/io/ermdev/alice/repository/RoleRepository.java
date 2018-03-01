package io.ermdev.alice.repository;

import io.ermdev.alice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("from Role where id=:roleId")
    Role findById(@Param("roleId") Long id);

    @Query("from Role")
    List<Role> findAll();
}
