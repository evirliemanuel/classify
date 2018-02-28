package io.ermdev.classify.data.service

import io.ermdev.classify.data.entity.User
import io.ermdev.classify.data.repository.UserRepository
import io.ermdev.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(@Autowired var userRepository: UserRepository) {

    fun findById(userId: Long): User {
        val user = userRepository.findById(userId)
        if (user != null) {
            return user
        } else {
            throw EntityException("No user found")
        }
    }

    fun findAll(): List<User>? = userRepository.findAll()
}