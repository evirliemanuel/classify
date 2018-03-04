package io.classify.controller

import io.classify.data.entity.User
import io.classify.data.service.UserService
import io.classify.dto.StudentDto
import io.classify.dto.TeacherDto
import io.classify.dto.UserDto
import io.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.mvc.ControllerLinkBuilder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("users")
class UserController(@Autowired val userService: UserService) {

    @GetMapping()
    fun getAll(@RequestParam(value = "username", required = false) username: String?): ResponseEntity<Any?> {
        return try {
            if (StringUtils.isEmpty(username)) {
                val dtoList = ArrayList<UserDto>()
                val users = userService.findAll()
                users.forEach({ user ->
                    val dto = UserDto(id = user.id, username = user.username, password = user.password)
                    val linkSelf = ControllerLinkBuilder
                            .linkTo(UserController::class.java)
                            .slash(dto.id)
                            .withSelfRel()
                    dto.add(linkSelf)
                    dtoList.add(dto)
                })
                ResponseEntity(dtoList, HttpStatus.OK)
            } else {
                val user = userService.findByUsername(username!!)
                val dto = UserDto(id = user.id, username = user.username, password = user.password)
                val linkSelf = ControllerLinkBuilder
                        .linkTo(UserController::class.java)
                        .slash(dto.id)
                        .withSelfRel()
                dto.add(linkSelf)
                ResponseEntity(dto, HttpStatus.OK)
            }
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

    @GetMapping("{userId}/students")
    fun getStudent(@PathVariable("userId") userId: Long): ResponseEntity<Any?> {
        return try {
            val student = userService.findStudent(userId)
            val dto = StudentDto(id = student.id, number = student.number, name = student.name)
            val linkSelf = ControllerLinkBuilder
                    .linkTo(StudentController::class.java)
                    .slash(dto.id)
                    .withSelfRel()
            dto.add(linkSelf)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{userId}/teachers")
    fun getTeacher(@PathVariable("userId") userId: Long): ResponseEntity<Any?> {
        return try {
            val teacher = userService.findTeacher(userId)
            val dto = TeacherDto(id = teacher.id, name = teacher.name, email = teacher.email)
            val linkSelf = ControllerLinkBuilder
                    .linkTo(TeacherController::class.java)
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