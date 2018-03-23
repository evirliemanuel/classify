package io.ermdev.classify.controller

import io.ermdev.classify.data.entity.Subject
import io.ermdev.classify.data.service.SubjectService
import io.ermdev.classify.dto.SubjectDto
import io.ermdev.classify.exception.EntityException
import io.ermdev.classify.hateoas.support.SubjectLinkSupport
import io.ermdev.classify.util.Error
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("subjects")
class SubjectController(private val subjectService: SubjectService) {

    @GetMapping
    fun getAll(): ResponseEntity<Any> {
        val dtoList = ArrayList<SubjectDto>()
        subjectService.findAll().forEach { subject ->
            val dto = SubjectDto(id = subject.id, name = subject.name, code = subject.code)
            dto.add(SubjectLinkSupport.self(dto.id))
            dtoList.add(dto)
        }
        return ResponseEntity(dtoList, HttpStatus.OK)
    }

    @GetMapping("{subjectId}")
    fun getById(@PathVariable("subjectId") subjectId: Long): ResponseEntity<Any> {
        return try {
            val subject = subjectService.findById(subjectId)
            val dto = SubjectDto(id = subject.id, name = subject.name, code = subject.code)
            dto.add(SubjectLinkSupport.self(dto.id))
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            val error = Error(status = 404, error = "Not Found", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    fun addSubject(@RequestBody body: Subject): ResponseEntity<Any> {
        return try {
            subjectService.save(body)
            ResponseEntity(HttpStatus.CREATED)
        } catch (e: EntityException) {
            val error = Error(status = 400, error = "Bad Request", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("{subjectId}")
    fun updateSubject(@PathVariable("subjectId") subjectId: Long,
                      @RequestBody body: Subject): ResponseEntity<Any> {
        return try {
            val subject = subjectService.findById(subjectId)
            subject.name = body.name
            subject.code = body.code
            subjectService.save(subject)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            val error = Error(status = 400, error = "Bad Request", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("{subjectId}")
    fun deleteSubject(@PathVariable("subjectId") subjectId: Long): ResponseEntity<Any> {
        subjectService.deleteById(subjectId)
        return ResponseEntity(HttpStatus.OK)
    }
}