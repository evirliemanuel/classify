package io.ermdev.classify.data.repository

import io.ermdev.classify.data.entity.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface RoleRepository : JpaRepository<Role, Long> {

    @Query("from Role where name=:name")
    fun findByName(@Param("name") name: String): Optional<Role>
}