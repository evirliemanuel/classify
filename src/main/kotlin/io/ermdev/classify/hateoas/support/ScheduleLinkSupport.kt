package io.ermdev.classify.hateoas.support

import io.ermdev.classify.controller.ScheduleController
import io.ermdev.classify.hateoas.Link
import org.springframework.hateoas.mvc.ControllerLinkBuilder

class ScheduleLinkSupport {
    companion object {
        fun self(id: Long): Link {
            val selfLink = ControllerLinkBuilder
                    .linkTo(ScheduleController::class.java)
                    .slash(id)
                    .withSelfRel()
            return Link(rel = selfLink.rel, href = selfLink.href)
        }

        fun lesson(id: Long): Link {
            val lessonsLink = ControllerLinkBuilder
                    .linkTo(ScheduleController::class.java)
                    .slash(id)
                    .slash("lessons")
                    .withRel("lesson")
            return Link(rel = lessonsLink.rel, href = lessonsLink.href)
        }
    }
}