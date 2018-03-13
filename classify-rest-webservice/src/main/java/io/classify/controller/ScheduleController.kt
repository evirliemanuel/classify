package io.classify.controller

import io.classify.data.entity.Schedule
import io.classify.data.service.ScheduleService
import io.classify.dto.ScheduleDto
import io.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.mvc.ControllerLinkBuilder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("schedules")
class ScheduleController(@Autowired private val scheduleService: ScheduleService) {

    @GetMapping()
    fun getAll(): ResponseEntity<Any?> {
        return try {
            val schedules = scheduleService.scheduleRepository.findAll()
            val dtoList = ArrayList<ScheduleDto>()
            schedules.forEach({ schedule ->
                val dto = ScheduleDto(id = schedule.id, date = schedule.date, room = schedule.room)
                val linkSelf = ControllerLinkBuilder
                        .linkTo(ScheduleController::class.java)
                        .slash(dto.id)
                        .withSelfRel()
                dto.add(linkSelf)
            })

            ResponseEntity(dtoList, HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{scheduleId}")
    fun getById(@PathVariable("scheduleId") scheduleId: Long): ResponseEntity<Any?> {
        return try {
            val schedule = scheduleService.findById(scheduleId)
            val dto = ScheduleDto(id = schedule.id, date = schedule.date, room = schedule.room)
            val linkSelf = ControllerLinkBuilder
                    .linkTo(ScheduleController::class.java)
                    .slash(dto.id)
                    .withSelfRel()
            dto.add(linkSelf)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    fun add(@RequestBody schedule: Schedule): ResponseEntity<Any?> {
        return try {
            scheduleService.scheduleRepository.save(schedule)
            ResponseEntity(HttpStatus.CREATED)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("{scheduleId}")
    fun update(@PathVariable("scheduleId") scheduleId: Long,
               @RequestBody schedule: Schedule): ResponseEntity<Any?> {
        return try {
            schedule.id = scheduleId
            scheduleService.scheduleRepository.save(schedule)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("{scheduleId}")
    fun delete(@PathVariable("scheduleId") scheduleId: Long): ResponseEntity<Any?> {
        return try {
            scheduleService.scheduleRepository.delete(scheduleId)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }
}