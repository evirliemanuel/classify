package io.ermdev.classify.dto

import io.ermdev.classify.hateoas.ResourceSupport

class SubjectDto(var id: Long = 0,

                 var name: String = "",

                 var code: String = "") : ResourceSupport()