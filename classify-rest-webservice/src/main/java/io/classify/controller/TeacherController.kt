package io.classify.controller

import io.classify.data.entity.Teacher
import io.classify.data.entity.User
import io.classify.data.service.TeacherService
import io.classify.data.service.UserService
import io.classify.dto.StudentDto
import io.classify.dto.SubjectDto
import io.classify.dto.TeacherDto
import io.classify.dto.UserDto
import io.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("teachers")
class TeacherController(@Autowired val teacherService: TeacherService,
                        @Autowired val userService: UserService) {

    @GetMapping
    fun getAll(): ResponseEntity<Any?> {
        return try {
            val dto = ArrayList<TeacherDto>()
            teacherService.findAll().parallelStream().forEach({ t -> dto.add(TeacherDto(t.id, t.name, t.email)) })
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{teacherId}")
    fun getById(@PathVariable("teacherId") teacherId: Long): ResponseEntity<Any?> {
        return try {
            val teacher = teacherService.findById(teacherId)
            val dto = TeacherDto(teacher.id, teacher.name, teacher.email)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{teacherId}/users")
    fun getUser(@PathVariable("teacherId") teacherId: Long): ResponseEntity<Any?> {
        return try {
            val user = teacherService.findUser(teacherId)
            val dto = UserDto(id = user.id, username = user.username, password = user.password)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }

    }

    @GetMapping("{teacherId}/subjects")
    fun getSubjects(@PathVariable("teacherId") teacherId: Long): ResponseEntity<Any?> {
        return try {
            val dto = ArrayList<SubjectDto>()
            val subjects = teacherService.findSubjects(teacherId)
            subjects.parallelStream().forEach({ subject ->
                dto.add(SubjectDto(id = subject.id, name = subject.name))
            })
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }

    }

    @GetMapping("{teacherId}/subjects/{subjectId}")
    fun getSubjectById(@PathVariable("teacherId") teacherId: Long,
                       @PathVariable("subjectId") subjectId: Long): ResponseEntity<Any?> {
        return try {
            val subject = teacherService.findSubjectById(teacherId, subjectId)
            val dto = SubjectDto(id = subject.id, name = subject.name)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }

    }

    @GetMapping("{teacherId}/lessons/{lessonId}/students")
    fun getStudents(@PathVariable("teacherId") teacherId: Long,
                    @PathVariable("lessonId") lessonId: Long): ResponseEntity<Any?> {
        return try {
            val dto = ArrayList<StudentDto>()
            val students = teacherService.findStudents(teacherId, lessonId)
            students.parallelStream().forEach({ student ->
                dto.add(StudentDto(id = student.id, number = student.number, name = student.name))
            })
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }

    }

    @PostMapping
    fun add(@RequestBody teacher: Teacher): ResponseEntity<Any?> {
        return try {
            teacher.user = User(0, teacher.email.split("@")[0].toLowerCase(), "123")
            teacherService.teacherRepository.save(teacher)
            ResponseEntity(HttpStatus.CREATED)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("{teacherId}/users/{userId}")
    fun addUser(@PathVariable("teacherId") teacherId: Long,
                @PathVariable("userId") userId: Long): ResponseEntity<Any?> {
        return try {
            val teacher = teacherService.findById(teacherId)
            teacher.user = userService.findById(userId)
            teacherService.teacherRepository.save(teacher)
            ResponseEntity(HttpStatus.CREATED)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("{teacherId}")
    fun update(@PathVariable("teacherId") teacherId: Long,
               @RequestBody teacher: Teacher): ResponseEntity<Any?> {
        return try {
            teacher.id = teacherId
            teacherService.teacherRepository.save(teacher)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("{teacherId}/users")
    fun updateUser(@PathVariable("teacherId") studentId: Long,
                   @RequestBody user: User): ResponseEntity<Any?> {
        return try {
            val oldUser = teacherService.findUser(studentId)
            user.id = oldUser.id
            userService.save(user)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("{teacherId}")
    fun delete(@PathVariable("teacherId") teacherId: Long): ResponseEntity<Any?> {
        return try {
            teacherService.teacherRepository.delete(teacherId)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("{teacherId}/users")
    fun deleteUser(@PathVariable("teacherId") teacherId: Long): ResponseEntity<Any?> {
        return try {
            teacherService.teacherRepository.deleteUser(teacherId)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }
}