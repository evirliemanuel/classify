package io.ermdev.classify.controller

import io.ermdev.classify.data.entity.Teacher
import io.ermdev.classify.data.entity.User
import io.ermdev.classify.data.service.TeacherService
import io.ermdev.classify.data.service.UserService
import io.ermdev.classify.dto.LessonDto
import io.ermdev.classify.dto.TeacherDto
import io.ermdev.classify.dto.UserDto
import io.ermdev.classify.exception.EntityException
import io.ermdev.classify.util.Error
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.mvc.ControllerLinkBuilder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("teachers")
class TeacherController(@Autowired val teacherService: TeacherService,
                        @Autowired val userService: UserService) {

    @GetMapping
    fun getAll(): ResponseEntity<Any> {
        return try {
            val dtoList = ArrayList<TeacherDto>()
            val teachers = teacherService.findAll()
            teachers.forEach { teacher ->
                val dto = TeacherDto(id = teacher.id, name = teacher.name, email = teacher.email)
                val linkSelf = ControllerLinkBuilder
                        .linkTo(TeacherController::class.java)
                        .slash(dto.id)
                        .withSelfRel()
                val linkUser = ControllerLinkBuilder
                        .linkTo(TeacherController::class.java)
                        .slash(dto.id)
                        .slash("users")
                        .withRel("user")
                val linkSubjects = ControllerLinkBuilder
                        .linkTo(TeacherController::class.java)
                        .slash(dto.id)
                        .slash("subjects")
                        .withRel("subjects")
                dto.add(linkUser)
                dto.add(linkSubjects)
                dto.add(linkSelf)
                dtoList.add(dto)
            }
            ResponseEntity(dtoList, HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{teacherId}")
    fun getById(@PathVariable("teacherId") teacherId: Long): ResponseEntity<Any?> {
        return try {
            val teacher = teacherService.findById(teacherId)
            val dto = TeacherDto(teacher.id, teacher.name, teacher.email)
            val linkSelf = ControllerLinkBuilder
                    .linkTo(TeacherController::class.java)
                    .slash(dto.id)
                    .withSelfRel()
            val linkUser = ControllerLinkBuilder
                    .linkTo(TeacherController::class.java)
                    .slash(dto.id)
                    .slash("users")
                    .withRel("user")
            val linkSubjects = ControllerLinkBuilder
                    .linkTo(TeacherController::class.java)
                    .slash(dto.id)
                    .slash("subjects")
                    .withRel("subjects")
            dto.add(linkUser)
            dto.add(linkSubjects)
            dto.add(linkSelf)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{teacherId}/users")
    fun getUser(@PathVariable("teacherId") teacherId: Long): ResponseEntity<Any> {
        return try {
            val user = teacherService.findUser(teacherId)
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

    @GetMapping("{teacherId}/lessons")
    fun getLessons(@PathVariable("teacherId") teacherId: Long): ResponseEntity<Any> {
        return try {
            val dtoList = ArrayList<LessonDto>()
            teacherService.findLessons(id = teacherId).forEach { lesson ->
                dtoList.add(LessonDto(id = lesson.id))
            }
            ResponseEntity(dtoList, HttpStatus.OK)
        } catch (e: Exception) {
            val error = Error(status = 404, error = "Not Found", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{teacherId}/lessons/{lessonId}")
    fun getLesson(@PathVariable("teacherId") teacherId: Long,
                  @PathVariable("lessonId") lessonId: Long): ResponseEntity<Any> {
        return try {
            val lesson = teacherService.findLesson(teacherId = teacherId, lessonId = lessonId)
            val dto = LessonDto(id = lesson.id)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: Exception) {
            val error = Error(status = 404, error = "Not Found", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    fun addTeacher(@RequestBody teacher: Teacher): ResponseEntity<Any?> {
        return try {
            teacher.user = User(0, teacher.email.split("@")[0].toLowerCase(), "123")
            teacherService.teacherRepository.save(teacher)
            ResponseEntity(HttpStatus.CREATED)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("{teacherId}/users/{userId}")
    fun addUser(@PathVariable("teacherId") teacherId: Long,
                @PathVariable("userId") userId: Long): ResponseEntity<Any?> {
        return try {
            val teacher = teacherService.findById(teacherId)
            teacher.user = userService.findById(userId)
            teacherService.teacherRepository.save(teacher)
            ResponseEntity(HttpStatus.CREATED)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("{teacherId}")
    fun updateTeacher(@PathVariable("teacherId") teacherId: Long,
                      @RequestBody teacher: Teacher): ResponseEntity<Any?> {
        return try {
            teacher.id = teacherId
            teacherService.teacherRepository.save(teacher)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("{teacherId}/users")
    fun updateUser(@PathVariable("teacherId") studentId: Long,
                   @RequestBody user: User): ResponseEntity<Any?> {
        return try {
            val oldUser = teacherService.findUser(studentId)
            user.id = oldUser.id
            userService.save(user)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("{teacherId}")
    fun deleteTeacher(@PathVariable("teacherId") teacherId: Long): ResponseEntity<Any?> {
        return try {
            teacherService.teacherRepository.deleteById(teacherId)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }
}