package io.ermdev.classify.controller

import io.ermdev.classify.data.entity.User
import io.ermdev.classify.data.service.UserService
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
    fun getUserById(@PathVariable("userId") userId: Long): ResponseEntity<User> {
        return ResponseEntity(userService.findById(userId), HttpStatus.FOUND)
    }

    @GetMapping()
    fun getAllUser(): ResponseEntity<List<User>?> {
        return ResponseEntity(userService.findAll(), HttpStatus.FOUND)
    }
}