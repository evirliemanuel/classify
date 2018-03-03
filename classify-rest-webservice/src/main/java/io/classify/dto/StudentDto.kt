package io.classify.dto

import org.springframework.hateoas.ResourceSupport

class StudentDto(var id: Long,

                 var number: Long,

                 var name: String) : ResourceSupport()