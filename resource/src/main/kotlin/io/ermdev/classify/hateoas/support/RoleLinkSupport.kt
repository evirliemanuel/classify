package io.ermdev.classify.hateoas.support

import io.ermdev.classify.controller.RoleController
import io.ermdev.classify.hateoas.Link
import org.springframework.hateoas.mvc.ControllerLinkBuilder

class RoleLinkSupport {
    companion object {
        fun self(id: Long): Link {
            val selfLink = ControllerLinkBuilder
                    .linkTo(RoleController::class.java)
                    .slash(id)
                    .withSelfRel()
            return Link(rel = selfLink.rel, href = selfLink.href)
        }
    }
}