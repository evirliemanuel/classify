package io.classify.controller

import io.classify.data.entity.Subject
import io.classify.data.service.SubjectService
import io.classify.dto.SubjectDto
import io.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("subjects")
class SubjectController(@Autowired val subjectService: SubjectService) {

    @GetMapping("{subjectId}")
    fun getById(@PathVariable("subjectId") subjectId: Long): ResponseEntity<Any?> {
        return try {
            val subject = subjectService.findById(subjectId)
            val dto = SubjectDto(subject.id, subject.name)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping()
    fun getAll(): ResponseEntity<Any?> {
        return try {
            val dto = ArrayList<SubjectDto>()
            subjectService.findAll().parallelStream().forEach({ s -> dto.add(SubjectDto(s.id, s.name)) })
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    fun add(@RequestBody subject: Subject): ResponseEntity<Any?> {
        return try {
            subjectService.save(subject)
            ResponseEntity(HttpStatus.CREATED)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("{subjectId}")
    fun update(@PathVariable("subjectId") subjectId: Long,
               @RequestBody subject: Subject): ResponseEntity<Any?> {
        return try {
            subject.id = subjectId
            subjectService.save(subject)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("{subjectId}")
    fun delete(@PathVariable("subjectId") subjectId: Long): ResponseEntity<Any?> {
        return try {
            subjectService.delete(subjectId)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }
}