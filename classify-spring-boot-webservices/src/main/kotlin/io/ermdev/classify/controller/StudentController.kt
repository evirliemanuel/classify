package io.ermdev.classify.controller

import io.ermdev.classify.data.entity.Student
import io.ermdev.classify.data.entity.User
import io.ermdev.classify.data.service.StudentService
import io.ermdev.classify.data.service.UserService
import io.ermdev.classify.dto.StudentDto
import io.ermdev.classify.dto.SubjectDto
import io.ermdev.classify.dto.TeacherDto
import io.ermdev.classify.dto.UserDto
import io.ermdev.classify.exception.EntityException
import io.ermdev.classify.hateoas.builder.TeacherLinkBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.mvc.ControllerLinkBuilder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("students")
class StudentController(@Autowired val studentService: StudentService,
                        @Autowired val userService: UserService) {

    @GetMapping
    fun getAll(): ResponseEntity<Any?> {
        return try {
            val dtoList = ArrayList<StudentDto>()
            val students = studentService.findAll()
            students.forEach({ student ->
                val dto = StudentDto(id = student.id, number = student.number, name = student.name)
                dto.add(TeacherLinkBuilder.self(dto.id))
                dto.add(TeacherLinkBuilder.lessons(dto.id))
                dto.add(TeacherLinkBuilder.user(dto.id))
                dtoList.add(dto)
            })
            ResponseEntity(dtoList, HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{studentId}")
    fun getById(@PathVariable("studentId") studentId: Long): ResponseEntity<Any?> {
        return try {
            val student = studentService.findById(studentId)
            val dto = StudentDto(id = student.id, number = student.number, name = student.name)
            dto.add(TeacherLinkBuilder.self(dto.id))
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{studentId}/users")
    fun getUser(@PathVariable("studentId") studentId: Long): ResponseEntity<Any?> {
        return try {
            val user = studentService.findUser(studentId)
            val dto = UserDto(id = user.id, username = user.username, password = user.password)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }

    }

    @GetMapping("{studentId}/subjects")
    fun getSubjects(@PathVariable("studentId") studentId: Long): ResponseEntity<Any?> {
        return try {
            val dtoList = ArrayList<SubjectDto>()
            val subjects = studentService.findSubjects(studentId)
            subjects.forEach({ subject ->
                val dto = SubjectDto(id = subject.id, name = subject.name)
                val linkSelf = ControllerLinkBuilder
                        .linkTo(SubjectController::class.java)
                        .slash(dto.id)
                        .withSelfRel()
                dto.add(linkSelf)
                dtoList.add(dto)
            })
            ResponseEntity(dtoList, HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }

    }

    @GetMapping("{studentId}/subjects/{subjectId}")
    fun getSubject(@PathVariable("studentId") studentId: Long,
                   @PathVariable("subjectId") subjectId: Long): ResponseEntity<Any?> {
        return try {
            val subject = studentService.findSubject(studentId, subjectId)
            val dto = SubjectDto(id = subject.id, name = subject.name)
            val linkSelf = ControllerLinkBuilder
                    .linkTo(SubjectController::class.java)
                    .slash(dto.id)
                    .withSelfRel()
            dto.add(linkSelf)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }

    }

    @GetMapping("{studentId}/teachers")
    fun getTeachers(@PathVariable("studentId") studentId: Long): ResponseEntity<Any?> {
        return try {
            val dtoList = ArrayList<TeacherDto>()
            val teachers = studentService.findTeachers(studentId)
            teachers.forEach({ teacher ->
                val dto = TeacherDto(id = teacher.id, name = teacher.name, email = teacher.email)
                dto.add(TeacherLinkBuilder.self(dto.id))
                dtoList.add(dto)
            })
            ResponseEntity(dtoList, HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }

    }

    @PostMapping
    fun add(@RequestBody student: Student): ResponseEntity<Any?> {
        return try {
            student.user = User(0, student.name.toLowerCase(), "${student.number}")
            studentService.studentRepository.save(student)
            ResponseEntity(HttpStatus.CREATED)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("{studentId}/users/{userId}")
    fun addUser(@PathVariable("studentId") studentId: Long,
                @PathVariable("userId") userId: Long): ResponseEntity<Any?> {
        return try {
            val student = studentService.findById(studentId)
            student.user = userService.findById(userId)
            studentService.studentRepository.save(student)
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
            studentService.studentRepository.save(student)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("{studentId}/users")
    fun updateUser(@PathVariable("studentId") studentId: Long,
                   @RequestBody user: User): ResponseEntity<Any?> {
        return try {
            val oldUser = studentService.findUser(studentId)
            user.id = oldUser.id
            userService.save(user)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("{studentId}")
    fun delete(@PathVariable("studentId") studentId: Long): ResponseEntity<Any?> {
        return try {
            studentService.studentRepository.deleteById(studentId)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("{studentId}/users")
    fun deleteUser(@PathVariable("studentId") studentId: Long): ResponseEntity<Any?> {
        return try {
            studentService.studentRepository.deleteUser(studentId)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }
}