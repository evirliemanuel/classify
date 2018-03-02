package io.classify.controller

import io.classify.data.entity.User
import io.classify.data.service.UserService
import io.classify.dto.UserDto
import io.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("users")
class UserController(@Autowired val userService: UserService) {

    @GetMapping("{userId}")
    fun getById(@PathVariable("userId") userId: Long): ResponseEntity<Any?> {
        return try {
            val user = userService.findById(userId)
            val dto = UserDto(user.id, user.username, user.password)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping()
    fun getAll(): ResponseEntity<Any?> {
        return try {
            val dto = ArrayList<UserDto>()
            userService.findAll().parallelStream().forEach({ u -> dto.add(UserDto(u.id, u.username, u.password)) })
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    fun add(@RequestBody user: User): ResponseEntity<Any?> {
        return try {
            userService.save(user)
            ResponseEntity(HttpStatus.CREATED)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("{userId}")
    fun update(@PathVariable("userId") userId: Long, @RequestBody user: User): ResponseEntity<Any?> {
        return try {
            user.id = userId
            userService.save(user)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("{userId}")
    fun delete(@PathVariable("userId") userId: Long): ResponseEntity<Any?> {
        return try {
            userService.delete(userId)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }
}