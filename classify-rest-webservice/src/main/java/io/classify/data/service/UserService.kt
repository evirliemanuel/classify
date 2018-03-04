package io.classify.data.service

import io.classify.data.entity.Student
import io.classify.data.entity.Teacher
import io.classify.data.entity.User
import io.classify.data.repository.UserRepository
import io.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(@Autowired var userRepository: UserRepository) {

    fun findAll(): List<User> {
        val users: List<User>? = userRepository.findAll()
        return users ?: throw EntityException("No user found")
    }

    fun findById(id: Long): User {
        val user: User? = userRepository.findById(id)
        return user ?: throw EntityException("No user found")
    }

    fun findByUsername(username: String): User {
        val user: User? = userRepository.findByUsername(username)
        return user ?: throw EntityException("No user found")
    }

    fun findStudent(userId: Long): Student {
        val student: Student? = userRepository.findStudent(userId)
        return student ?: throw EntityException("No student found")
    }

    fun findTeacher(userId: Long): Teacher {
        val teacher: Teacher? = userRepository.findTeacher(userId)
        return teacher ?: throw EntityException("No teacher found")
    }
}