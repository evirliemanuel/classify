package io.ermdev.classify.hateoas.builder

import io.ermdev.classify.controller.StudentController
import io.ermdev.classify.hateoas.Link
import org.springframework.hateoas.mvc.ControllerLinkBuilder

class StudentLinkBuilder {
    companion object {
        fun self(id: Long): Link {
            val selfLink = ControllerLinkBuilder
                    .linkTo(StudentController::class.java)
                    .slash(id)
                    .withSelfRel()
            return Link(rel = selfLink.rel, href = selfLink.href)
        }

        fun lessons(id: Long): Link {
            val lessonsLink = ControllerLinkBuilder
                    .linkTo(StudentController::class.java)
                    .slash(id)
                    .slash("lessons")
                    .withRel("lessons")
            return Link(rel = lessonsLink.rel, href = lessonsLink.href)
        }

        fun user(id: Long): Link {
            val userLink = ControllerLinkBuilder
                    .linkTo(StudentController::class.java)
                    .slash(id)
                    .slash("users")
                    .withRel("user")
            return Link(rel = userLink.rel, href = userLink.href)
        }
    }
}