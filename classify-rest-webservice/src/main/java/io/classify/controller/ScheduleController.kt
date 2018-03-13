package io.classify.controller

import io.classify.data.service.ScheduleService
import io.classify.dto.ScheduleDto
import io.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.mvc.ControllerLinkBuilder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}