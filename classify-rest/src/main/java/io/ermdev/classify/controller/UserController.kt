package io.ermdev.classify.controller

import io.ermdev.classify.data.service.UserService
import io.ermdev.classify.dto.UserDto
import io.ermdev.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("users")
class UserController(@Autowired var userService: UserService) {

    @GetMapping("{userId}")
    fun getById(@PathVariable("userId") userId: Long): ResponseEntity<Any?> {
        return try {
            val user = userService.findById(userId)
            val dto = UserDto(user.id, user.username, user.password)
            ResponseEntity(dto, HttpStatus.FOUND)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping()
    fun getAll(): ResponseEntity<Any?> {
        return try {
            val dto = ArrayList<UserDto>()
            userService.findAll().parallelStream().forEach({ u -> dto.add(UserDto(u.id, u.username, u.password)) })
            ResponseEntity(dto, HttpStatus.FOUND)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }
}