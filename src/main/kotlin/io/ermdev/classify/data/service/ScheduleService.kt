package io.ermdev.classify.data.service

import io.ermdev.classify.data.entity.Schedule
import io.ermdev.classify.data.repository.ScheduleRepository
import io.ermdev.classify.exception.EntityException
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils

@Service
class ScheduleService(private val scheduleRepository: ScheduleRepository) {

    fun findAll() = scheduleRepository.findAll()

    fun findById(id: Long): Schedule {
        return scheduleRepository.findById(id)
                .orElseThrow { EntityException("No schedule entity with id $id exists!") }
    }

    fun save(schedule: Schedule) {
        if (StringUtils.isEmpty(schedule.day)) {
            throw EntityException("day cannot be empty")
        }
        if (!schedule.day.matches(Regex("^[a-zA-Z0-9]+$"))) {
            throw EntityException("day cannot contain special characters")
        }
        if (StringUtils.isEmpty(schedule.room)) {
            throw EntityException("day cannot be empty")
        }
        if (!schedule.room.matches(Regex("^[a-zA-Z0-9]+( [a-zA-Z0-9]+)*$"))) {
            throw EntityException("room cannot contain special characters")
        }
        scheduleRepository.save(schedule)
    }

    fun deleteById(id: Long) = scheduleRepository.deleteById(id)
}