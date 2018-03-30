package io.ermdev.classify.data.service

import io.ermdev.classify.data.entity.Role
import io.ermdev.classify.data.repository.RoleRepository
import io.ermdev.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils

@Service
class RoleService(@Autowired val roleRepository: RoleRepository) {

    fun findAll(): List<Role> = roleRepository.findAll()

    fun funById(id: Long): Role {
        return roleRepository.findById(id)
                .orElseThrow { EntityException("No role with id $id exists") }
    }

    fun save(role: Role) {
        if (StringUtils.isEmpty(role.name)) {
            throw EntityException("name cannot be empty")
        }
        roleRepository.save(role)
    }

    fun deleteById(id: Long) = roleRepository.deleteById(id)
}