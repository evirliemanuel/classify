package io.ermdev.classify.data.service

import io.ermdev.alice.entity.User
import io.ermdev.alice.exception.EntityException
import io.ermdev.alice.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(@Autowired var userRepository: UserRepository) {

    fun findById(userId: Long): User {
        var user = userRepository.findById(userId)
        if (user != null) {
            return user
        } else {
            throw EntityException("No user found")
        }
    }

    fun findAll(): List<User> = userRepository.findAll()
}