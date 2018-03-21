package io.ermdev.classify.controller

import io.ermdev.classify.data.entity.Student
import io.ermdev.classify.data.entity.User
import io.ermdev.classify.data.service.StudentService
import io.ermdev.classify.data.service.UserService
import io.ermdev.classify.dto.StudentDto
import io.ermdev.classify.dto.UserDto
import io.ermdev.classify.exception.EntityException
import io.ermdev.classify.hateoas.builder.StudentLinkBuilder
import io.ermdev.classify.hateoas.builder.UserLinkBuilder
import io.ermdev.classify.util.Error
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("students")
class StudentController(@Autowired val studentService: StudentService,
                        @Autowired val userService: UserService) {

    @GetMapping
    fun getAll(): ResponseEntity<Any> {
        val dtoList = ArrayList<StudentDto>()
        val students = studentService.findAll()
        students.forEach { student ->
            val dto = StudentDto(id = student.id, number = student.number, name = student.name)
            dto.add(StudentLinkBuilder.self(dto.id))
            dto.add(StudentLinkBuilder.lessons(dto.id))
            dto.add(StudentLinkBuilder.user(dto.id))
            dtoList.add(dto)
        }
        return ResponseEntity(dtoList, HttpStatus.OK)
    }

    @GetMapping("{studentId}")
    fun getById(@PathVariable("studentId") studentId: Long): ResponseEntity<Any> {
        return try {
            val student = studentService.findById(studentId)
            val dto = StudentDto(id = student.id, number = student.number, name = student.name)
            dto.add(StudentLinkBuilder.self(dto.id))
            dto.add(StudentLinkBuilder.lessons(dto.id))
            dto.add(StudentLinkBuilder.user(dto.id))
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            val error = Error(status = 404, error = "Not Found", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{studentId}/users")
    fun getUser(@PathVariable("studentId") studentId: Long): ResponseEntity<Any?> {
        return try {
            val user = studentService.findUser(studentId)
            val dto = UserDto(id = user.id, username = user.username, password = user.password)
            dto.add(UserLinkBuilder.self(id = dto.id))
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            val error = Error(status = 404, error = "Not Found", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.NOT_FOUND)
        }

    }

    @PostMapping
    fun addStudent(@RequestBody student: Student): ResponseEntity<Any> {
        return try {
            studentService.save(student)
            ResponseEntity(HttpStatus.CREATED)
        } catch (e: EntityException) {
            val error = Error(status = 400, error = "Bad Request", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("{studentId}")
    fun updateStudent(@PathVariable("studentId") studentId: Long,
                      @RequestBody student: Student): ResponseEntity<Any?> {
        return try {
            student.id = studentId
            studentService.save(student)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            val error = Error(status = 400, error = "Bad Request", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("{studentId}/users/{userId}")
    fun updateUser(@PathVariable("studentId") studentId: Long,
                   @PathVariable("userId") userId: Long): ResponseEntity<Any> {
        return try {
            val student = studentService.findById(studentId)
            student.user = userService.findById(userId)
            studentService.save(student)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            val error = Error(status = 400, error = "Bad Request", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("{studentId}")
    fun delete(@PathVariable("studentId") studentId: Long): ResponseEntity<Any> {
        studentService.deleteById(studentId)
        return ResponseEntity(HttpStatus.OK)
    }
}