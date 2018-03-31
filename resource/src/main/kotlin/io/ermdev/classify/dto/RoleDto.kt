package io.ermdev.classify.dto

import io.ermdev.classify.hateoas.ResourceSupport

class RoleDto(var id: Long = 0,

              var name: String = "") : ResourceSupport()