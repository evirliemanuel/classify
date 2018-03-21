package io.ermdev.classify.controller

import io.ermdev.classify.data.entity.Subject
import io.ermdev.classify.data.service.SubjectService
import io.ermdev.classify.dto.SubjectDto
import io.ermdev.classify.exception.EntityException
import io.ermdev.classify.hateoas.builder.SubjectLinkBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("subjects")
class SubjectController(@Autowired val subjectService: SubjectService) {

    @GetMapping
    fun getAll(): ResponseEntity<Any?> {
        val dtoList = ArrayList<SubjectDto>()
        subjectService.findAll().forEach { subject ->
            val dto = SubjectDto(id = subject.id, name = subject.name, code = subject.code)
            dto.add(SubjectLinkBuilder.self(dto.id))
            dtoList.add(dto)
        }
        return ResponseEntity(dtoList, HttpStatus.OK)
    }

    @GetMapping("{subjectId}")
    fun getById(@PathVariable("subjectId") subjectId: Long): ResponseEntity<Any?> {
        return try {
            val subject = subjectService.findById(subjectId)
            val dto = SubjectDto(id = subject.id, name = subject.name, code = subject.code)
            dto.add(SubjectLinkBuilder.self(dto.id))
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    fun addSubject(@RequestBody subject: Subject): ResponseEntity<Any?> {
        return try {
            subjectService.save(subject)
            ResponseEntity(HttpStatus.CREATED)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("{subjectId}")
    fun updateSubject(@PathVariable("subjectId") subjectId: Long,
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
    fun deleteSubject(@PathVariable("subjectId") subjectId: Long): ResponseEntity<Any?> {
        subjectService.deleteById(subjectId)
        return ResponseEntity(HttpStatus.OK)
    }
}