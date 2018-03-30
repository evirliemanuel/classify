package io.ermdev.classify.data.repository

import io.ermdev.classify.data.entity.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long>