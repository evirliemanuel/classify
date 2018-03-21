package io.ermdev.classify.controller

import io.ermdev.classify.data.entity.User
import io.ermdev.classify.data.service.UserService
import io.ermdev.classify.dto.UserDto
import io.ermdev.classify.exception.EntityException
import io.ermdev.classify.hateoas.builder.UserLinkBuilder
import io.ermdev.classify.util.Error
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("users")
class UserController(@Autowired val userService: UserService) {

    @GetMapping()
    fun getAll(@RequestParam(value = "username", required = false) username: String?): ResponseEntity<Any> {
        if (StringUtils.isEmpty(username)) {
            val dtoList = ArrayList<UserDto>()
            val users = userService.findAll()
            users.forEach { user ->
                val dto = UserDto(id = user.id, username = user.username, password = user.password)
                dto.add(UserLinkBuilder.self(id = dto.id))
                dtoList.add(dto)
            }
            return ResponseEntity(dtoList, HttpStatus.OK)
        } else {
            return try {
                val user = userService.findByUsername(username!!)
                val dto = UserDto(id = user.id, username = user.username, password = user.password)
                dto.add(UserLinkBuilder.self(id = dto.id))
                ResponseEntity(dto, HttpStatus.OK)
            } catch (e: EntityException) {
                val error = Error(status = 404, error = "Not Found", message = e.message ?: "")
                ResponseEntity(error, HttpStatus.NOT_FOUND)
            }
        }
    }

    @GetMapping("{userId}")
    fun getById(@PathVariable("userId") userId: Long): ResponseEntity<Any> {
        return try {
            val user = userService.findById(userId)
            val dto = UserDto(id = user.id, username = user.username, password = user.password)
            dto.add(UserLinkBuilder.self(id = dto.id))
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            val error = Error(status = 404, error = "Not Found", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    fun addUser(@RequestBody user: User): ResponseEntity<Any> {
        return try {
            userService.save(user)
            ResponseEntity(HttpStatus.CREATED)
        } catch (e: EntityException) {
            val error = Error(status = 400, error = "Bad Request", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("{userId}")
    fun updateUser(@PathVariable("userId") userId: Long, @RequestBody user: User): ResponseEntity<Any> {
        return try {
            user.id = userId
            userService.save(user)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            val error = Error(status = 400, error = "Bad Request", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("{userId}")
    fun deleteUser(@PathVariable("userId") userId: Long): ResponseEntity<Any> {
        userService.deleteById(userId)
        return ResponseEntity(HttpStatus.OK)
    }
}