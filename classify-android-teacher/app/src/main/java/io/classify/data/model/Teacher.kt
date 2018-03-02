package io.classify.data.model

class Teacher(var id: Long = 0,

              var name: String = "",

              var email: String = "") {

    override fun toString(): String {
        return "${id}, ${name}"
    }
}