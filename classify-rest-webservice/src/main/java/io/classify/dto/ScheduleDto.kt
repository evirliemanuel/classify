package io.classify.dto

import org.springframework.hateoas.ResourceSupport
import java.util.*

class ScheduleDto(
        var id: Long = 0,
        var date: Date = Date(),
        var room: String = "") : ResourceSupport()