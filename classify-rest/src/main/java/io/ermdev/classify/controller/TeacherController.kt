package io.ermdev.classify.controller

import io.ermdev.classify.data.entity.Teacher
import io.ermdev.classify.data.entity.User
import io.ermdev.classify.data.service.TeacherService
import io.ermdev.classify.dto.TeacherDto
import io.ermdev.classify.dto.UserDto
import io.ermdev.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("teachers")
class TeacherController(@Autowired val teacherService: TeacherService) {

    @GetMapping("{teacherId}")
    fun getById(@PathVariable("teacherId") teacherId: Long): ResponseEntity<Any?> {
        return try {
            val teacher = teacherService.findById(teacherId)
            val dto = TeacherDto(teacher.id, teacher.name, teacher.email)
            ResponseEntity(dto, HttpStatus.FOUND)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("all")
    fun getAll(): ResponseEntity<Any?> {
        return try {
            val dto = ArrayList<TeacherDto>()
            teacherService.findAll().parallelStream().forEach({ t -> dto.add(TeacherDto(t.id, t.name, t.email)) })
            ResponseEntity(dto, HttpStatus.FOUND)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{teacherId}/user")
    fun getUserById(@PathVariable("teacherId") teacherId: Long): ResponseEntity<Any> {
        val user = teacherService.findById(teacherId).user
        return if (user != null) {
            val dto = UserDto(user.id, user.username, user.password)
            ResponseEntity(dto, HttpStatus.FOUND)
        } else {
            ResponseEntity("No user found", HttpStatus.NOT_FOUND)
        }

    }

    @PostMapping
    fun add(@RequestBody teacher: Teacher): ResponseEntity<Void> {
        teacher.user = User(0, teacher.email.split("@")[0].toLowerCase(), "123")
        teacherService.save(teacher)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @PutMapping("{teacherId}")
    fun update(@PathVariable("teacherId") teacherId: Long,
               @RequestBody teacher: Teacher): ResponseEntity<Void> {
        teacher.id = teacherId
        teacherService.save(teacher)
        return ResponseEntity(HttpStatus.OK)
    }

    @DeleteMapping("{teacherId}")
    fun delete(@PathVariable("teacherId") teacherId: Long): ResponseEntity<Void> {
        teacherService.delete(teacherId)
        return ResponseEntity(HttpStatus.OK)
    }
}