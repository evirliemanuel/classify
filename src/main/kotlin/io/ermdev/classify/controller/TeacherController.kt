package io.ermdev.classify.controller

import io.ermdev.classify.data.entity.Teacher
import io.ermdev.classify.data.service.TeacherService
import io.ermdev.classify.data.service.UserService
import io.ermdev.classify.dto.LessonDto
import io.ermdev.classify.dto.TeacherDto
import io.ermdev.classify.dto.UserDto
import io.ermdev.classify.exception.EntityException
import io.ermdev.classify.hateoas.support.TeacherLinkSupport
import io.ermdev.classify.hateoas.support.UserLinkSupport
import io.ermdev.classify.util.Error
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("teachers")
class TeacherController(@Autowired private val teacherService: TeacherService,
                        @Autowired private val userService: UserService) {

    @GetMapping
    fun getAll(): ResponseEntity<Any> {
        val dtoList = ArrayList<TeacherDto>()
        val teachers = teacherService.findAll()
        teachers.forEach { teacher ->
            val dto = TeacherDto(id = teacher.id, name = teacher.name, email = teacher.email)
            dto.add(TeacherLinkSupport.self(dto.id))
            dto.add(TeacherLinkSupport.lessons(dto.id))
            dto.add(TeacherLinkSupport.user(dto.id))
            dtoList.add(dto)
        }
        return ResponseEntity(dtoList, HttpStatus.OK)
    }

    @GetMapping("{teacherId}")
    fun getById(@PathVariable("teacherId") teacherId: Long): ResponseEntity<Any> {
        return try {
            val teacher = teacherService.findById(teacherId)
            val dto = TeacherDto(teacher.id, teacher.name, teacher.email)
            dto.add(TeacherLinkSupport.self(dto.id))
            dto.add(TeacherLinkSupport.lessons(dto.id))
            dto.add(TeacherLinkSupport.user(dto.id))
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
            dto.add(UserLinkSupport.self(id = dto.id))
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{teacherId}/lessons")
    fun getLessons(@PathVariable("teacherId") teacherId: Long): ResponseEntity<Any> {
        val dtoList = ArrayList<LessonDto>()
        teacherService.findLessons(id = teacherId).forEach { lesson ->
            dtoList.add(LessonDto(id = lesson.id))
        }
        return ResponseEntity(dtoList, HttpStatus.OK)
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
    fun addTeacher(@RequestBody body: Teacher): ResponseEntity<Any> {
        return try {
            teacherService.save(body)
            ResponseEntity(HttpStatus.CREATED)
        } catch (e: EntityException) {
            val error = Error(status = 400, error = "Bad Request", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("{teacherId}")
    fun updateTeacher(@PathVariable("teacherId") teacherId: Long,
                      @RequestBody body: Teacher): ResponseEntity<Any> {
        return try {
            val teacher = teacherService.findById(teacherId)
            teacher.name = body.name
            teacher.email = body.email
            teacherService.save(teacher)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            val error = Error(status = 400, error = "Bad Request", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("{teacherId}/users/{userId}")
    fun updateUser(@PathVariable("teacherId") teacherId: Long,
                   @PathVariable("userId") userId: Long): ResponseEntity<Any> {
        return try {
            val teacher = teacherService.findById(teacherId)
            teacher.user = userService.findById(userId)
            teacherService.save(teacher)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            val error = Error(status = 400, error = "Bad Request", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("{teacherId}")
    fun deleteTeacher(@PathVariable("teacherId") teacherId: Long): ResponseEntity<Any> {
        teacherService.deleteById(teacherId)
        return ResponseEntity(HttpStatus.OK)
    }
}