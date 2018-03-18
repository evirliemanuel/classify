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
import org.springframework.hateoas.mvc.ControllerLinkBuilder
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
            val dtoList = ArrayList<TeacherDto>()
            val teachers = teacherService.findAll()
            teachers.forEach({ teacher ->
                val dto = TeacherDto(id = teacher.id, name = teacher.name, email = teacher.email)
                val linkSelf = ControllerLinkBuilder
                        .linkTo(TeacherController::class.java)
                        .slash(dto.id)
                        .withSelfRel()
                val linkUser = ControllerLinkBuilder
                        .linkTo(TeacherController::class.java)
                        .slash(dto.id)
                        .slash("users")
                        .withRel("user")
                val linkSubjects = ControllerLinkBuilder
                        .linkTo(TeacherController::class.java)
                        .slash(dto.id)
                        .slash("subjects")
                        .withRel("subjects")
                dto.add(linkUser)
                dto.add(linkSubjects)
                dto.add(linkSelf)
                dtoList.add(dto)
            })
            ResponseEntity(dtoList, HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{teacherId}")
    fun getById(@PathVariable("teacherId") teacherId: Long): ResponseEntity<Any?> {
        return try {
            val teacher = teacherService.findById(teacherId)
            val dto = TeacherDto(teacher.id, teacher.name, teacher.email)
            val linkSelf = ControllerLinkBuilder
                    .linkTo(TeacherController::class.java)
                    .slash(dto.id)
                    .withSelfRel()
            val linkUser = ControllerLinkBuilder
                    .linkTo(TeacherController::class.java)
                    .slash(dto.id)
                    .slash("users")
                    .withRel("user")
            val linkSubjects = ControllerLinkBuilder
                    .linkTo(TeacherController::class.java)
                    .slash(dto.id)
                    .slash("subjects")
                    .withRel("subjects")
            dto.add(linkUser)
            dto.add(linkSubjects)
            dto.add(linkSelf)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{teacherId}/users")
    fun getUser(@PathVariable("teacherId") teacherId: Long): ResponseEntity<Any?> {
        return try {
            val user = teacherService.findUser(teacherId)
            val dto = UserDto(id = user.id, username = user.username, password = user.password)
            val linkSelf = ControllerLinkBuilder
                    .linkTo(UserController::class.java)
                    .slash(dto.id)
                    .withSelfRel()
            dto.add(linkSelf)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }

    }

    @GetMapping("{teacherId}/subjects")
    fun getSubjects(@PathVariable("teacherId") teacherId: Long): ResponseEntity<Any?> {
        return try {
            val dtoList = ArrayList<SubjectDto>()
            val subjects = teacherService.findSubjects(teacherId)
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

    @GetMapping("{teacherId}/subjects/{subjectId}")
    fun getSubject(@PathVariable("teacherId") teacherId: Long,
                   @PathVariable("subjectId") subjectId: Long): ResponseEntity<Any?> {
        return try {
            val subject = teacherService.findSubject(teacherId, subjectId)
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

    @GetMapping("{teacherId}/lessons/{lessonId}/subjects")
    fun getSubjectByLesson(@PathVariable("teacherId") teacherId: Long,
                           @PathVariable("lessonId") lessonId: Long): ResponseEntity<Any?> {
        return try {
            val subject = teacherService.findSubjectByLesson(teacherId, lessonId)
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

    @GetMapping("{teacherId}/lessons/{lessonId}/students")
    fun getStudents(@PathVariable("teacherId") teacherId: Long,
                    @PathVariable("lessonId") lessonId: Long): ResponseEntity<Any?> {
        return try {
            val dtoList = ArrayList<StudentDto>()
            val students = teacherService.findStudents(teacherId, lessonId)
            students.forEach({ student ->
                val dto = StudentDto(id = student.id, number = student.number, name = student.name)
                val linkSelf = ControllerLinkBuilder
                        .linkTo(StudentController::class.java)
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

    @GetMapping("{teacherId}/lessons/{lessonId}/students/{studentId}")
    fun getStudent(@PathVariable("teacherId") teacherId: Long,
                   @PathVariable("lessonId") lessonId: Long,
                   @PathVariable("studentId") studentId: Long): ResponseEntity<Any?> {
        return try {
            val student = teacherService.findStudent(teacherId, lessonId, studentId)
            val dto = StudentDto(id = student.id, number = student.number, name = student.name)
            val linkSelf = ControllerLinkBuilder
                    .linkTo(StudentController::class.java)
                    .slash(dto.id)
                    .withSelfRel()
            dto.add(linkSelf)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
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
            userService.userRepository.save(user)
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