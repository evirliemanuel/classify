package io.ermdev.classify.dto

import io.ermdev.classify.hateoas.ResourceSupport
import java.sql.Time

class ScheduleDto(
        var id: Long = 0,

        var day: String = "",

        var room: String = "",

        var start: Time = Time(System.currentTimeMillis()),

        var end: Time = Time(System.currentTimeMillis())) : ResourceSupport()