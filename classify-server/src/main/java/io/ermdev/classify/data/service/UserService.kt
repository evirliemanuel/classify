package io.ermdev.classify.data.service

import io.ermdev.alice.entity.User
import io.ermdev.alice.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(@Autowired var userRepository: UserRepository) {

    fun findById(userId: Long): User = userRepository.findById(userId)

    fun findAll(): List<User> = userRepository.findAll()
}