package io.ermdev.classify.hateoas.support

import io.ermdev.classify.controller.UserController
import io.ermdev.classify.hateoas.Link
import org.springframework.hateoas.mvc.ControllerLinkBuilder

class UserLinkSupport {
    companion object {
        fun self(id: Long): Link {
            val selfLink = ControllerLinkBuilder
                    .linkTo(UserController::class.java)
                    .slash(id)
                    .withSelfRel()
            return Link(rel = selfLink.rel, href = selfLink.href)
        }
    }
}