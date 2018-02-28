package io.ermdev.classify.controller

import io.ermdev.classify.data.entity.User
import io.ermdev.classify.data.service.TeacherService
import io.ermdev.classify.dto.TeacherDto
import io.ermdev.classify.dto.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("teachers")
class TeacherController(@Autowired val teacherService: TeacherService) {

    @GetMapping("{teacherId}")
    fun getTeacherById(@PathVariable("teacherId") teacherId: Long): ResponseEntity<TeacherDto> {
        val teacher = teacherService.findById(teacherId)
        val dto = TeacherDto(teacher.id, teacher.name, teacher.email)
        return ResponseEntity(dto, HttpStatus.FOUND)
    }

    @GetMapping()
    fun getAllTeacher(): ResponseEntity<List<TeacherDto>> {
        val teachers = teacherService.findAll()
        val dto = ArrayList<TeacherDto>()
        teachers.parallelStream().forEach({ t -> dto.add(TeacherDto(t.id, t.name, t.email)) })
        return ResponseEntity(dto, HttpStatus.FOUND)
    }

    @GetMapping("{teacherId}/user")
    fun getUser(@PathVariable("teacherId") teacherId: Long): ResponseEntity<UserDto> {
        var user = teacherService.findById(teacherId).user
        if (user == null) {
            user = User()
        }
        val dto = UserDto(user.id, user.username, user.password)
        return ResponseEntity(dto, HttpStatus.FOUND)
    }
}