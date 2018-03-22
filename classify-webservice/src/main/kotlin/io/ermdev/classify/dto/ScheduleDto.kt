package io.ermdev.classify.dto

import io.ermdev.classify.hateoas.ResourceSupport
import java.sql.Time

class ScheduleDto(
        val id: Long = 0,

        val day: String = "",

        val room: String = "",

        val start: Time = Time(System.currentTimeMillis()),

        val end: Time = Time(System.currentTimeMillis())) : ResourceSupport()