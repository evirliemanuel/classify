package io.classify.controller

import io.classify.data.entity.Lesson
import io.classify.data.service.LessonService
import io.classify.dto.LessonDto
import io.classify.dto.SubjectDto
import io.classify.dto.TeacherDto
import io.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("lessons")
class LessonController(@Autowired val lessonService: LessonService) {

    @GetMapping("{lessonId}")
    fun getById(@PathVariable("lessonId") lessonId: Long): ResponseEntity<Any?> {
        return try {
            val lesson = lessonService.findById(lessonId)
            val dto = LessonDto(id = lesson.id)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping
    fun getAll(): ResponseEntity<Any?> {
        return try {
            val dto = ArrayList<LessonDto>()
            lessonService.findAll().parallelStream().forEach({ l -> dto.add(LessonDto(id = l.id)) })
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{lessonId}")
    fun getTeacher(@PathVariable("lessonId") lessonId: Long): ResponseEntity<Any?> {
        return try {
            val teacher = lessonService.findTeacher(lessonId)
            val dto = TeacherDto(teacher.id, teacher.name, teacher.email)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{lessonId}")
    fun getSubject(@PathVariable("lessonId") lessonId: Long): ResponseEntity<Any?> {
        return try {
            val subject = lessonService.findSubject(lessonId)
            val dto = SubjectDto(id = subject.id, name = subject.name)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    fun add(@RequestBody lesson: Lesson): ResponseEntity<Any?> {
        return try {
            lessonService.save(lesson)
            ResponseEntity(HttpStatus.CREATED)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("{lessonId}")
    fun update(@PathVariable("lessonId") lessonId: Long,
               @RequestBody lesson: Lesson): ResponseEntity<Any?> {
        return try {
            lesson.id = lessonId
            lessonService.save(lesson)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("{lessonId}")
    fun delete(@PathVariable("lessonId") lessonId: Long): ResponseEntity<Any?> {
        return try {
            lessonService.delete(lessonId)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }
}