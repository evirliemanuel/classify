package io.classify.controller

import io.classify.data.entity.Lesson
import io.classify.data.service.LessonService
import io.classify.data.service.StudentService
import io.classify.data.service.SubjectService
import io.classify.data.service.TeacherService
import io.classify.dto.LessonDto
import io.classify.dto.StudentDto
import io.classify.dto.SubjectDto
import io.classify.dto.TeacherDto
import io.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("lessons")
class LessonController(@Autowired val lessonService: LessonService,
                       @Autowired val teacherService: TeacherService,
                       @Autowired val subjectService: SubjectService,
                       @Autowired val studentService: StudentService) {

    @GetMapping("{lessonId}")
    fun getById(@PathVariable("lessonId") lessonId: Long): ResponseEntity<Any?> {
        return try {
            val lesson = lessonService.findById(lessonId)
            val dto = LessonDto(id = lesson.id)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping
    fun getAll(): ResponseEntity<Any?> {
        return try {
            val dto = ArrayList<LessonDto>()
            lessonService.findAll().parallelStream().forEach({ l -> dto.add(LessonDto(id = l.id)) })
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{lessonId}/teachers")
    fun getTeacher(@PathVariable("lessonId") lessonId: Long): ResponseEntity<Any?> {
        return try {
            val teacher = lessonService.findTeacher(lessonId)
            val dto = TeacherDto(teacher.id, teacher.name, teacher.email)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{lessonId}/subjects")
    fun getSubject(@PathVariable("lessonId") lessonId: Long): ResponseEntity<Any?> {
        return try {
            val subject = lessonService.findSubject(lessonId)
            val dto = SubjectDto(id = subject.id, name = subject.name)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
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
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{lessonId}/students/{studentId}")
    fun getStudentById(@PathVariable("lessonId") lessonId: Long,
                       @PathVariable("studentId") studentId: Long): ResponseEntity<Any?> {
        return try {
            val student = lessonService.findStudentById(lessonId, studentId)
            val dto = StudentDto(id = student.id, number = student.number, name = student.name)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    fun add(@RequestBody lesson: Lesson): ResponseEntity<Any?> {
        return try {
            lessonService.save(lesson)
            ResponseEntity(HttpStatus.CREATED)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("{lessonId}/teachers/{teacherId}")
    fun addTeacher(@PathVariable("lessonId") lessonId: Long,
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

    @PostMapping("{lessonId}/subjects/{subjectId}")
    fun addSubject(@PathVariable("lessonId") lessonId: Long,
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

    @PostMapping("{lessonId}/students/{studentId}")
    fun addStudent(@PathVariable("lessonId") lessonId: Long,
                   @PathVariable("studentId") studentId: Long): ResponseEntity<Any?> {
        return try {
            val lesson = lessonService.findById(lessonId)
            val isExists = try {
                lessonService.findStudentById(lessonId, studentId)
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
    fun update(@PathVariable("lessonId") lessonId: Long,
               @RequestBody lesson: Lesson): ResponseEntity<Any?> {
        return try {
            lesson.id = lessonId
            lessonService.save(lesson)
            ResponseEntity(HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("{lessonId}")
    fun delete(@PathVariable("lessonId") lessonId: Long): ResponseEntity<Any?> {
        return try {
            lessonService.delete(lessonId)
            ResponseEntity(HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("{lessonId}/teachers")
    fun deleteTeacher(@PathVariable("lessonId") lessonId: Long): ResponseEntity<Any?> {
        return try {
            val lesson = lessonService.findById(lessonId)
            lesson.teacher = null
            lessonService.save(lesson)
            ResponseEntity(HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("{lessonId}/subjects")
    fun deleteSubject(@PathVariable("lessonId") lessonId: Long): ResponseEntity<Any?> {
        return try {
            val lesson = lessonService.findById(lessonId)
            lesson.subject = null
            lessonService.save(lesson)
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