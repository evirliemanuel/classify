package io.ermdev.classify.controller

import io.ermdev.classify.data.service.TeacherService
import io.ermdev.classify.dto.TeacherDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("teachers")
class TeacherController(@Autowired var teacherService: TeacherService) {

    @GetMapping("{teacherId}")
    fun getById(@PathVariable("teacherId") teacherId: Long): ResponseEntity<TeacherDto> {
        val teacher = teacherService.findById(teacherId)
        val dto = TeacherDto(teacher.id, teacher.name, teacher.email)
        return ResponseEntity(dto, HttpStatus.FOUND)
    }

    @GetMapping()
    fun getById(): ResponseEntity<List<TeacherDto>> {
        val dto = ArrayList<TeacherDto>()
        teacherService.findAll().parallelStream().forEach({ t -> dto.add(TeacherDto(t.id, t.name, t.email)) })
        return ResponseEntity(dto, HttpStatus.FOUND)
    }
}