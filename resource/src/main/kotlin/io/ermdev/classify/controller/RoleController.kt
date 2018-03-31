package io.ermdev.classify.controller

import io.ermdev.classify.data.service.RoleService
import io.ermdev.classify.dto.RoleDto
import io.ermdev.classify.dto.UserDto
import io.ermdev.classify.exception.EntityException
import io.ermdev.classify.hateoas.support.UserLinkSupport
import io.ermdev.classify.util.Error
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("roles")
class RoleController(@Autowired val roleService: RoleService) {

    @GetMapping
    fun getAll(@RequestParam(value = "username", required = false) username: String?): ResponseEntity<Any> {
        val dtoList = ArrayList<RoleDto>()
        val roles = roleService.findAll()
        roles.forEach { role ->
            val dto = RoleDto(id = role.id, name = role.name)
            dto.add(UserLinkSupport.self(id = dto.id))
            dtoList.add(dto)
        }
        return ResponseEntity(dtoList, HttpStatus.OK)
    }
}