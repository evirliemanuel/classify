package io.ermdev.classify.dto

import io.ermdev.classify.hateoas.ResourceSupport

class UserDto(var id: Long = 0,

              var username: String = "",

              var password: String = "") : ResourceSupport()
