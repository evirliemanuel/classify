package io.classify.controller

import io.classify.data.entity.User
import io.classify.data.service.UserService
import io.classify.dto.UserDto
import io.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.mvc.ControllerLinkBuilder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("users")
class UserController(@Autowired val userService: UserService) {

    @GetMapping()
    fun getAll(): ResponseEntity<Any?> {
        return try {
            val dtoList = ArrayList<UserDto>()
            val users = userService.findAll()
            users.forEach({ user ->
                val dto = UserDto(id = user.id, username = user.username, password = user.password)
                val linkSelf = ControllerLinkBuilder
                        .linkTo(UserController::class.java)
                        .slash(dto.id)
                        .withSelfRel()
                dto.add(linkSelf)
            })
            ResponseEntity(dtoList, HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{userId}")
    fun getById(@PathVariable("userId") userId: Long): ResponseEntity<Any?> {
        return try {
            val user = userService.findById(userId)
            val dto = UserDto(id = user.id, username = user.username, password = user.password)
            val linkSelf = ControllerLinkBuilder
                    .linkTo(UserController::class.java)
                    .slash(dto.id)
                    .withSelfRel()
            dto.add(linkSelf)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    fun add(@RequestBody user: User): ResponseEntity<Any?> {
        return try {
            userService.userRepository.save(user)
            ResponseEntity(HttpStatus.CREATED)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("{userId}")
    fun update(@PathVariable("userId") userId: Long, @RequestBody user: User): ResponseEntity<Any?> {
        return try {
            user.id = userId
            userService.userRepository.save(user)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("{userId}")
    fun delete(@PathVariable("userId") userId: Long): ResponseEntity<Any?> {
        return try {
            userService.userRepository.delete(userId)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }
}