package io.classify.controller

import io.classify.data.entity.Student
import io.classify.data.entity.User
import io.classify.data.service.StudentService
import io.classify.dto.StudentDto
import io.classify.dto.UserDto
import io.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("students")
class StudentController(@Autowired val studentService: StudentService) {

    @GetMapping("{studentId}")
    fun getById(@PathVariable("studentId") studentId: Long): ResponseEntity<Any?> {
        return try {
            val student = studentService.findById(studentId)
            val dto = StudentDto(student.id, student.number, student.name)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping
    fun getAll(): ResponseEntity<Any?> {
        return try {
            val dto = ArrayList<StudentDto>()
            studentService.findAll().parallelStream().forEach({ s -> dto.add(StudentDto(s.id, s.number, s.name)) })
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{studentId}/user")
    fun getUser(@PathVariable("studentId") studentId: Long): ResponseEntity<Any?> {
        return try {
            val user = studentService.findById(studentId).user
            val dto = UserDto(user.id, user.username, user.password)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }

    }

    @PostMapping
    fun add(@RequestBody student: Student): ResponseEntity<Any?> {
        return try {
            student.user = User(0, student.name.toLowerCase(), "${student.number}")
            studentService.save(student)
            ResponseEntity(HttpStatus.CREATED)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("{studentId}")
    fun update(@PathVariable("studentId") studentId: Long,
               @RequestBody student: Student): ResponseEntity<Any?> {
        return try {
            student.id = studentId
            studentService.save(student)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("{studentId}/user")
    fun updateUser(@PathVariable("studentId") studentId: Long,
                   @RequestBody user: User): ResponseEntity<Any?> {
        return try {
            val student = studentService.findById(studentId)
            student.user = user
            studentService.save(student)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("{studentId}")
    fun delete(@PathVariable("studentId") studentId: Long): ResponseEntity<Any?> {
        return try {
            studentService.delete(studentId)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }
}