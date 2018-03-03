package io.classify.dto

import org.springframework.hateoas.ResourceSupport

class UserDto(var id: Long = 0,

              var username: String = "",

              var password: String = "") : ResourceSupport()
