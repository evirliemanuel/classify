package io.classify.dto

import org.springframework.hateoas.ResourceSupport

class SubjectDto(var id: Long = 0,

                 var name: String = "") : ResourceSupport()