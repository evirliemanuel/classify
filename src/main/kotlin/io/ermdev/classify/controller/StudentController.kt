package io.ermdev.classify.controller

import io.ermdev.classify.data.entity.Student
import io.ermdev.classify.data.service.StudentService
import io.ermdev.classify.data.service.UserService
import io.ermdev.classify.dto.LessonDto
import io.ermdev.classify.dto.StudentDto
import io.ermdev.classify.dto.UserDto
import io.ermdev.classify.exception.EntityException
import io.ermdev.classify.hateoas.support.StudentLinkSupport
import io.ermdev.classify.hateoas.support.UserLinkSupport
import io.ermdev.classify.util.Error
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("students")
class StudentController(@Autowired private val studentService: StudentService,
                        @Autowired private val userService: UserService) {

    @GetMapping
    fun getAll(): ResponseEntity<Any> {
        val dtoList = ArrayList<StudentDto>()
        val students = studentService.findAll()
        students.forEach { student ->
            val dto = StudentDto(id = student.id, number = student.number, name = student.name)
            dto.add(StudentLinkSupport.self(dto.id))
            dto.add(StudentLinkSupport.lessons(dto.id))
            dto.add(StudentLinkSupport.user(dto.id))
            dtoList.add(dto)
        }
        return ResponseEntity(dtoList, HttpStatus.OK)
    }

    @GetMapping("{studentId}")
    fun getById(@PathVariable("studentId") studentId: Long): ResponseEntity<Any> {
        return try {
            val student = studentService.findById(studentId)
            val dto = StudentDto(id = student.id, number = student.number, name = student.name)
            dto.add(StudentLinkSupport.self(dto.id))
            dto.add(StudentLinkSupport.lessons(dto.id))
            dto.add(StudentLinkSupport.user(dto.id))
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            val error = Error(status = 404, error = "Not Found", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{studentId}/lessons")
    fun getLessons(@PathVariable("studentId") studentId: Long): ResponseEntity<Any> {
        val dtoList = ArrayList<LessonDto>()
        studentService.findLessons(id = studentId).forEach { lesson ->
            dtoList.add(LessonDto(id = lesson.id))
        }
        return ResponseEntity(dtoList, HttpStatus.OK)
    }

    @GetMapping("{studentId}/lessons/{lessonId}")
    fun getLesson(@PathVariable("studentId") studentId: Long,
                  @PathVariable("lessonId") lessonId: Long): ResponseEntity<Any> {
        return try {
            val lesson = studentService.findLesson(studentId = studentId, lessonId = lessonId)
            val dto = LessonDto(id = lesson.id)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: Exception) {
            val error = Error(status = 404, error = "Not Found", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{studentId}/users")
    fun getUser(@PathVariable("studentId") studentId: Long): ResponseEntity<Any> {
        return try {
            val user = studentService.findUser(studentId)
            val dto = UserDto(id = user.id, username = user.username, password = user.password)
            dto.add(UserLinkSupport.self(id = dto.id))
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            val error = Error(status = 404, error = "Not Found", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.NOT_FOUND)
        }

    }

    @PostMapping
    fun addStudent(@RequestBody body: Student): ResponseEntity<Any> {
        return try {
            studentService.save(body)
            ResponseEntity(HttpStatus.CREATED)
        } catch (e: EntityException) {
            val error = Error(status = 400, error = "Bad Request", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("{studentId}")
    fun updateStudent(@PathVariable("studentId") studentId: Long,
                      @RequestBody body: Student): ResponseEntity<Any> {
        return try {
            val student = studentService.findById(studentId)
            student.name = body.name
            student.number = body.number
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