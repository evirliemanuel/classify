package io.ermdev.classify.hateoas.support

import io.ermdev.classify.controller.LessonController
import io.ermdev.classify.hateoas.Link
import org.springframework.hateoas.mvc.ControllerLinkBuilder

class LessonLinkSupport {
    companion object {
        fun self(id: Long): Link {
            val selfLink = ControllerLinkBuilder
                    .linkTo(LessonController::class.java)
                    .slash(id)
                    .withSelfRel()
            return Link(rel = selfLink.rel, href = selfLink.href)
        }

        fun schedules(id: Long): Link {
            val lessonsLink = ControllerLinkBuilder
                    .linkTo(LessonController::class.java)
                    .slash(id)
                    .slash("schedules")
                    .withRel("schedules")
            return Link(rel = lessonsLink.rel, href = lessonsLink.href)
        }

        fun students(id: Long): Link {
            val userLink = ControllerLinkBuilder
                    .linkTo(LessonController::class.java)
                    .slash(id)
                    .slash("students")
                    .withRel("students")
            return Link(rel = userLink.rel, href = userLink.href)
        }

        fun subject(id: Long): Link {
            val userLink = ControllerLinkBuilder
                    .linkTo(LessonController::class.java)
                    .slash(id)
                    .slash("subjects")
                    .withRel("subject")
            return Link(rel = userLink.rel, href = userLink.href)
        }

        fun teacher(id: Long): Link {
            val userLink = ControllerLinkBuilder
                    .linkTo(LessonController::class.java)
                    .slash(id)
                    .slash("teachers")
                    .withRel("teacher")
            return Link(rel = userLink.rel, href = userLink.href)
        }
    }
}