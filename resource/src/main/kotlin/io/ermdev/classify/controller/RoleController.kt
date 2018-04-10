package io.ermdev.classify.controller

import io.ermdev.classify.data.entity.Role
import io.ermdev.classify.data.service.RoleService
import io.ermdev.classify.dto.RoleDto
import io.ermdev.classify.exception.EntityException
import io.ermdev.classify.hateoas.support.RoleLinkSupport
import io.ermdev.classify.util.Error
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("roles")
class RoleController(@Autowired val roleService: RoleService) {

    @GetMapping
    fun getAll(@RequestParam("name") name: String?): ResponseEntity<Any> {
        if (name != null) {
            return try {
                val role = roleService.findByName(name)
                val dto = RoleDto(id = role.id, name = role.name)
                dto.add(RoleLinkSupport.self(id = dto.id))
                ResponseEntity(dto, HttpStatus.OK)
            } catch (e: EntityException) {
                val error = Error(status = 404, error = "Not Found", message = e.message ?: "")
                ResponseEntity(error, HttpStatus.NOT_FOUND)
            }
        } else {
            val dtoList = ArrayList<RoleDto>()
            val roles = roleService.findAll()
            roles.forEach { role ->
                val dto = RoleDto(id = role.id, name = role.name)
                dto.add(RoleLinkSupport.self(id = dto.id))
                dtoList.add(dto)
            }
            return ResponseEntity(dtoList, HttpStatus.OK)
        }
    }

    @GetMapping("{roleId}")
    fun getById(@PathVariable("roleId") roleId: Long): ResponseEntity<Any> {
        return try {
            val role = roleService.findById(roleId)
            val dto = RoleDto(id = role.id, name = role.name)
            dto.add(RoleLinkSupport.self(id = dto.id))
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            val error = Error(status = 404, error = "Not Found", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    fun addRole(@RequestBody body: Role): ResponseEntity<Any> {
        return try {
            roleService.save(body)
            ResponseEntity(HttpStatus.CREATED)
        } catch (e: EntityException) {
            val error = Error(status = 400, error = "Bad Request", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("{roleId}")
    fun updateRole(@PathVariable("roleId") roleId: Long,
                   @RequestBody body: Role): ResponseEntity<Any> {
        return try {
            val role = roleService.findById(roleId)
            role.name = body.name
            roleService.save(role)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            val error = Error(status = 400, error = "Bad Request", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("{roleId}")
    fun deleteRole(@PathVariable("roleId") roleId: Long): ResponseEntity<Any> {
        roleService.deleteById(roleId)
        return ResponseEntity(HttpStatus.OK)
    }
}