package io.classify.dto

import org.springframework.hateoas.ResourceSupport

class TeacherDto(var id: Long = 0,

                 var name: String = "",

                 var email: String = ""): ResourceSupport()