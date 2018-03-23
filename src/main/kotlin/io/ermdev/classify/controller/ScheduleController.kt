package io.ermdev.classify.controller

import io.ermdev.classify.data.entity.Schedule
import io.ermdev.classify.data.service.ScheduleService
import io.ermdev.classify.dto.ScheduleDto
import io.ermdev.classify.exception.EntityException
import io.ermdev.classify.util.Error
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("schedules")
class ScheduleController(private val scheduleService: ScheduleService) {

    @GetMapping
    fun getAll(): ResponseEntity<Any> {
        val schedules: List<Schedule> = scheduleService.findAll()
        val dtoList = ArrayList<ScheduleDto>()
        schedules.forEach { schedule ->
            val dto = ScheduleDto(id = schedule.id, day = schedule.day, room = schedule.room, start = schedule.start,
                    end = schedule.end)
            dtoList.add(dto)
            System.out.println(schedule)
        }
        return ResponseEntity(dtoList, HttpStatus.OK)
    }

    @GetMapping("{scheduleId}")
    fun getById(@PathVariable("scheduleId") scheduleId: Long): ResponseEntity<Any> {
        return try {
            val schedule = scheduleService.findById(scheduleId)
            val dto = ScheduleDto(id = schedule.id, day = schedule.day, room = schedule.room, start = schedule.start,
                    end = schedule.end)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            val error = Error(status = 404, error = "Not Found", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    fun addUser(@RequestBody body: Schedule): ResponseEntity<Any> {
        return try {
            scheduleService.save(body)
            ResponseEntity(HttpStatus.CREATED)
        } catch (e: EntityException) {
            val error = Error(status = 400, error = "Bad Request", message = e.message ?: "")
            ResponseEntity(error, HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("{scheduleId}")
    fun updateUser(@PathVariable("scheduleId") scheduleId: Long,
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
    fun deleteUser(@PathVariable("scheduleId") scheduleId: Long): ResponseEntity<Any> {
        scheduleService.deleteById(scheduleId)
        return ResponseEntity(HttpStatus.OK)
    }
}