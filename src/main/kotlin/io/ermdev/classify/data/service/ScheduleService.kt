package io.ermdev.classify.data.service

import io.ermdev.classify.data.entity.Lesson
import io.ermdev.classify.data.entity.Schedule
import io.ermdev.classify.data.repository.ScheduleRepository
import io.ermdev.classify.exception.EntityException
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils

@Service
class ScheduleService(private val scheduleRepository: ScheduleRepository) {

    fun findAll(): List<Schedule> = scheduleRepository.findAll()

    fun findById(id: Long): Schedule {
        return scheduleRepository.findById(id)
                .orElseThrow { EntityException("No schedule entity with id $id exists!") }
    }

    fun findByDay(day: String): List<Schedule> {
        val anyMatch: String = when (day.toLowerCase().trim()) {
            "mon", "1" -> "Monday"
            "tue", "2" -> "Tuesday"
            "wed", "3" -> "Wednesday"
            "thu", "4" -> "Thursday"
            "fri", "5" -> "Friday"
            "sat", "6" -> "Saturday"
            "sun", "7", "0" -> "Sunday"
            else -> day
        }
        return scheduleRepository.findByDay(anyMatch)
    }

    fun findByDayAndTeacherId(day: String, id: Long): List<Schedule> {
        val anyMatch: String = when (day.toLowerCase().trim()) {
            "mon", "1" -> "Monday"
            "tue", "2" -> "Tuesday"
            "wed", "3" -> "Wednesday"
            "thu", "4" -> "Thursday"
            "fri", "5" -> "Friday"
            "sat", "6" -> "Saturday"
            "sun", "7", "0" -> "Sunday"
            else -> day
        }
        return scheduleRepository.findByDayAndTeacherId(day = anyMatch, teacherId = id)
    }

    fun findByTeacherId(id: Long): List<Schedule> = scheduleRepository.findByTeacherId(id)

    fun findLesson(id: Long): Lesson {
        return scheduleRepository.findLesson(scheduleId = id)
                ?: throw EntityException("No lesson entity found!")
    }

    fun save(schedule: Schedule) {
        if (StringUtils.isEmpty(schedule.day)) {
            throw EntityException("day cannot be empty")
        }
        when (schedule.day.toLowerCase().trim()) {
            "monday", "mon", "1" -> schedule.day = "Monday"
            "tuesday", "tue", "2" -> schedule.day = "Tuesday"
            "wednesday", "wed", "3" -> schedule.day = "Wednesday"
            "thursday", "thu", "4" -> schedule.day = "Thursday"
            "friday", "fri", "5" -> schedule.day = "Friday"
            "saturday", "sat", "6" -> schedule.day = "Saturday"
            "sunday", "sun", "7", "0" -> schedule.day = "Sunday"
            else -> throw EntityException("day must be valid")
        }
        if (StringUtils.isEmpty(schedule.room)) {
            throw EntityException("room cannot be empty")
        }
        if (!schedule.room.matches(Regex("^[a-zA-Z0-9]+( [a-zA-Z0-9]+)*$"))) {
            throw EntityException("room cannot contain special characters")
        }
        scheduleRepository.save(schedule)
    }

    fun deleteById(id: Long) = scheduleRepository.deleteById(id)
}