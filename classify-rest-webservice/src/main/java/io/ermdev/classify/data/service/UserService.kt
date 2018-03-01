package io.ermdev.classify.data.service

import io.ermdev.classify.data.entity.User
import io.ermdev.classify.data.repository.UserRepository
import io.ermdev.classify.exception.EntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(@Autowired var userRepository: UserRepository) {

    fun findById(userId: Long): User {
        val user: User? = userRepository.findById(userId)
        return user ?: throw EntityException("No user found")
    }

    fun findAll(): List<User> {
        val users: List<User>? = userRepository.findAll()
        return users ?: throw EntityException("No user found")
    }

    fun save(user: User) = userRepository.save(user)

    fun delete(user: User) = userRepository.delete(user)

    fun delete(userId: Long) = userRepository.delete(userId)
}