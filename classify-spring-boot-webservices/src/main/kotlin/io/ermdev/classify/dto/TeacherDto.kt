package io.ermdev.classify.dto

import io.ermdev.classify.hateoas.ResourceSupport

class TeacherDto(var id: Long = 0,

                 var name: String = "",

                 var email: String = ""): ResourceSupport()