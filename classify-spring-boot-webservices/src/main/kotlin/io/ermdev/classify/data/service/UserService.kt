package io.ermdev.classify.data.service

import io.ermdev.classify.data.entity.Student
import io.ermdev.classify.data.entity.Teacher
import io.ermdev.classify.data.entity.User
import io.ermdev.classify.data.repository.UserRepository
import io.ermdev.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils

@Service
class UserService(@Autowired private var userRepository: UserRepository) {

    fun findAll(): List<User> = userRepository.findAll()

    fun findById(id: Long): User {
        return userRepository.findById(id)
                .orElseThrow { EntityException("No user found") }
    }

    fun findByUsername(username: String): User {
        return userRepository.findByUsername(username)
                .orElseThrow { EntityException("No user found") }
    }

    fun findStudent(userId: Long): Student {
        return userRepository.findStudent(userId)
                .orElseThrow { EntityException("No student found") }
    }

    fun findTeacher(userId: Long): Teacher {
        return userRepository.findTeacher(userId)
                .orElseThrow { EntityException("No teacher found") }
    }

    fun save(user: User) {
        if (StringUtils.isEmpty(user.username)) {
            throw EntityException("username cannot be empty")
        }
        if (!user.username.matches(Regex("^[0-9a-zA-Z]+$"))) {
            throw EntityException("username cannot contain special characters")
        }
        if (StringUtils.isEmpty(user.password)) {
            throw EntityException("password cannot be empty")
        }
        if (!user.password.matches(Regex("^[0-9a-zA-Z]+$"))) {
            throw EntityException("password cannot contain special characters")
        }
        userRepository.save(user)
    }

    fun deleteById(userId: Long) = userRepository.deleteById(userId)
}