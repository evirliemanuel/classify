package io.ermdev.classify.dto

import io.ermdev.classify.hateoas.ResourceSupport

class StudentDto(var id: Long,

                 var number: Long,

                 var name: String) : ResourceSupport()