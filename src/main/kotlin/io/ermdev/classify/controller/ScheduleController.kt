package io.ermdev.classify.controller

import io.ermdev.classify.data.entity.Schedule
import io.ermdev.classify.data.service.LessonService
import io.ermdev.classify.data.service.ScheduleService
import io.ermdev.classify.dto.LessonDto
import io.ermdev.classify.dto.ScheduleDto
import io.ermdev.classify.exception.EntityException
import io.ermdev.classify.hateoas.support.LessonLinkSupport
import io.ermdev.classify.hateoas.support.ScheduleLinkSupport
import io.ermdev.classify.util.Error
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("schedules")
class ScheduleController(@Autowired private val scheduleService: ScheduleService,
                         @Autowired private val lessonService: LessonService) {

    @GetMapping
    fun getAll(@RequestParam("day", required = false) day: String?): ResponseEntity<Any> {
        val dtoList = ArrayList<ScheduleDto>()
        return if (StringUtils.isEmpty(day)) {
            scheduleService.findAll().forEach { schedule ->
                val dto = ScheduleDto(id = schedule.id, day = schedule.day, room = schedule.room,
                        start = schedule.start, end = schedule.end)
                dto.add(ScheduleLinkSupport.self(dto.id))
                dto.add(ScheduleLinkSupport.lesson(dto.id))
                dtoList.add(dto)
            }
            ResponseEntity(dtoList, HttpStatus.OK)
        } else {
            scheduleService.findByDay(day ?: "").forEach { schedule ->
                val dto = ScheduleDto(id = schedule.id, day = schedule.day, room = schedule.room,
                        start = schedule.start, end = schedule.end)
                dto.add(ScheduleLinkSupport.self(dto.id))
                dto.add(ScheduleLinkSupport.lesson(dto.id))
                dtoList.add(dto)
            }
            ResponseEntity(dtoList, HttpStatus.OK)
        }
    }

    @GetMapping("{scheduleId}")
    fun getById(@PathVariable("scheduleId") scheduleId: Long): ResponseEntity<Any> {
        return try {
            val schedule = scheduleService.findById(scheduleId)
            val dto = ScheduleDto(id = schedule.id, day = schedule.day, room = schedule.room, start = schedule.start,
                    end = schedule.end)
            dto.add(ScheduleLinkSupport.self(dto.id))
            dto.add(ScheduleLinkSupport.lesson(dto.id))
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            val error = Error(status = 404, error = "Not Found", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{scheduleId}/lessons")
    fun getLesson(@PathVariable("scheduleId") scheduleId: Long): ResponseEntity<Any> {
        return try {
            val lesson = scheduleService.findLesson(scheduleId)
            val dto = LessonDto(id = lesson.id)
            dto.add(LessonLinkSupport.self(dto.id))
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            val error = Error(status = 404, error = "Not Found", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    fun addSchedule(@RequestParam("lessonId") lessonId: Long,
                    @RequestBody body: Schedule): ResponseEntity<Any> {
        return try {
            body.lesson = lessonService.findById(lessonId)
            scheduleService.save(body)
            ResponseEntity(HttpStatus.CREATED)
        } catch (e: EntityException) {
            val error = Error(status = 400, error = "Bad Request", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("{scheduleId}")
    fun updateSchedule(@PathVariable("scheduleId") scheduleId: Long,
                       @RequestBody body: Schedule): ResponseEntity<Any> {
        return try {
            val schedule = scheduleService.findById(scheduleId)
            schedule.day = body.day
            schedule.room = body.room
            schedule.start = body.start
            schedule.end = body.end
            scheduleService.save(schedule)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            val error = Error(status = 400, error = "Bad Request", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("{scheduleId}")
    fun deleteSchedule(@PathVariable("scheduleId") scheduleId: Long): ResponseEntity<Any> {
        scheduleService.deleteById(scheduleId)
        return ResponseEntity(HttpStatus.OK)
    }
}