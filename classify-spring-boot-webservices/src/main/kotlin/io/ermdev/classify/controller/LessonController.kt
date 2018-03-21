package io.ermdev.classify.controller

import io.ermdev.classify.data.entity.Lesson
import io.ermdev.classify.data.service.LessonService
import io.ermdev.classify.data.service.StudentService
import io.ermdev.classify.data.service.SubjectService
import io.ermdev.classify.data.service.TeacherService
import io.ermdev.classify.dto.LessonDto
import io.ermdev.classify.dto.StudentDto
import io.ermdev.classify.dto.SubjectDto
import io.ermdev.classify.dto.TeacherDto
import io.ermdev.classify.exception.EntityException
import io.ermdev.classify.hateoas.builder.TeacherLinkBuilder
import io.ermdev.classify.util.Error
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("lessons")
class LessonController(@Autowired private val lessonService: LessonService,
                       @Autowired private val teacherService: TeacherService,
                       @Autowired private val subjectService: SubjectService,
                       @Autowired private val studentService: StudentService) {

    @GetMapping
    fun getAll(): ResponseEntity<Any> {
        val dto = ArrayList<LessonDto>()
        lessonService.findAll().parallelStream().forEach({ lesson -> dto.add(LessonDto(id = lesson.id)) })
        return ResponseEntity(dto, HttpStatus.OK)
    }

    @GetMapping("{lessonId}")
    fun getById(@PathVariable("lessonId") lessonId: Long): ResponseEntity<Any> {
        return try {
            val lesson = lessonService.findById(lessonId)
            val dto = LessonDto(id = lesson.id)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: Exception) {
            val error = Error(status = 404, error = "Not Found", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{lessonId}/teachers")
    fun getTeacher(@PathVariable("lessonId") lessonId: Long): ResponseEntity<Any?> {
        return try {
            val teacher = lessonService.findTeacher(lessonId)
            val dto = TeacherDto(teacher.id, teacher.name, teacher.email)
            dto.add(TeacherLinkBuilder.self(dto.id))
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: Exception) {
            val error = Error(status = 404, error = "Not Found", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{lessonId}/subjects")
    fun getSubject(@PathVariable("lessonId") lessonId: Long): ResponseEntity<Any?> {
        return try {
            val subject = lessonService.findSubject(lessonId)
            val dto = SubjectDto(id = subject.id, name = subject.name)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: Exception) {
            val error = Error(status = 404, error = "Not Found", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{lessonId}/students")
    fun getStudents(@PathVariable("lessonId") lessonId: Long): ResponseEntity<Any?> {
        return try {
            val dto = ArrayList<StudentDto>()
            lessonService.findStudents(lessonId).parallelStream().forEach({ student ->
                dto.add(StudentDto(id = student.id, number = student.number, name = student.name))
            })
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: Exception) {
            val error = Error(status = 404, error = "Not Found", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{lessonId}/students/{studentId}")
    fun getStudent(@PathVariable("lessonId") lessonId: Long,
                   @PathVariable("studentId") studentId: Long): ResponseEntity<Any?> {
        return try {
            val student = lessonService.findStudent(lessonId, studentId)
            val dto = StudentDto(id = student.id, number = student.number, name = student.name)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: Exception) {
            val error = Error(status = 404, error = "Not Found", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    fun addLesson(@RequestBody lesson: Lesson,
                  @RequestParam("subjectId") subjectId: Long,
                  @RequestParam("teacherId") teacherId: Long): ResponseEntity<Any> {
        return try {
            lesson.subject = subjectService.findById(subjectId)
            lesson.teacher = teacherService.findById(teacherId)
            lessonService.save(lesson)
            ResponseEntity(HttpStatus.CREATED)
        } catch (e: EntityException) {
            val error = Error(status = 400, error = "Bad Request", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("{lessonId}/students/{studentId}")
    fun addStudent(@PathVariable("lessonId") lessonId: Long,
                   @PathVariable("studentId") studentId: Long): ResponseEntity<Any?> {
        return try {
            val lesson = lessonService.findById(lessonId)
            val isExists = try {
                lessonService.findStudent(lessonId, studentId)
                true
            } catch (e: EntityException) {
                false
            }
            if (!isExists) {
                lesson.students.add(studentService.findById(studentId))
                lessonService.save(lesson)
            }
            ResponseEntity(HttpStatus.CREATED)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("{lessonId}")
    fun updateLesson(@PathVariable("lessonId") lessonId: Long,
               @RequestBody lesson: Lesson): ResponseEntity<Any?> {
        return try {
            lesson.id = lessonId
            lessonService.save(lesson)
            ResponseEntity(HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("{lessonId}/subjects/{subjectId}")
    fun updateSubject(@PathVariable("lessonId") lessonId: Long,
                      @PathVariable("subjectId") subjectId: Long): ResponseEntity<Any?> {
        return try {
            val lesson = lessonService.findById(lessonId)
            lesson.subject = subjectService.findById(subjectId)
            lessonService.save(lesson)
            ResponseEntity(HttpStatus.CREATED)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("{lessonId}/teachers/{teacherId}")
    fun updateTeacher(@PathVariable("lessonId") lessonId: Long,
                   @PathVariable("teacherId") teacherId: Long): ResponseEntity<Any?> {
        return try {
            val lesson = lessonService.findById(lessonId)
            lesson.teacher = teacherService.findById(teacherId)
            lessonService.save(lesson)
            ResponseEntity(HttpStatus.CREATED)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("{lessonId}")
    fun deleteLesson(@PathVariable("lessonId") lessonId: Long): ResponseEntity<Any?> {
        return try {
            lessonService.deleteById(lessonId)
            ResponseEntity(HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("{lessonId}/students/{studentId}")
    fun deleteStudent(@PathVariable("lessonId") lessonId: Long,
                      @PathVariable("studentId") studentId: Long): ResponseEntity<Any?> {
        return try {
            lessonService.deleteStudent(lessonId, studentId)
            ResponseEntity(HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }
}