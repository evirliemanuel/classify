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
class UserService(@Autowired var userRepository: UserRepository) {

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
            throw EntityException("Username is required")
        }
        if (StringUtils.isEmpty(user.password)) {
            throw EntityException("Password is required")
        }
        userRepository.save(user)
    }
}