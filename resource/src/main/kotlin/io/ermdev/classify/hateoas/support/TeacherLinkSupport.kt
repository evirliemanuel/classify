package io.ermdev.classify.hateoas.support

import io.ermdev.classify.controller.TeacherController
import io.ermdev.classify.hateoas.Link
import org.springframework.hateoas.mvc.ControllerLinkBuilder

class TeacherLinkSupport {
    companion object {
        fun self(id: Long): Link {
            val selfLink = ControllerLinkBuilder
                    .linkTo(TeacherController::class.java)
                    .slash(id)
                    .withSelfRel()
            return Link(rel = selfLink.rel, href = selfLink.href)
        }

        fun lessons(id: Long): Link {
            val lessonsLink = ControllerLinkBuilder
                    .linkTo(TeacherController::class.java)
                    .slash(id)
                    .slash("lessons")
                    .withRel("lessons")
            return Link(rel = lessonsLink.rel, href = lessonsLink.href)
        }

        fun user(id: Long): Link {
            val userLink = ControllerLinkBuilder
                    .linkTo(TeacherController::class.java)
                    .slash(id)
                    .slash("users")
                    .withRel("user")
            return Link(rel = userLink.rel, href = userLink.href)
        }
    }
}