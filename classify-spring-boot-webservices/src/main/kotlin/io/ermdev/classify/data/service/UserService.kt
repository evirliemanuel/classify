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
                .orElseThrow { EntityException("No user entity with id $id exists!") }
    }

    fun findByUsername(username: String): User {
        return userRepository.findByUsername(username)
                .orElseThrow { EntityException("No user entity with username $username exists!") }
    }

    fun findStudent(id: Long): Student {
        return userRepository.findStudent(id)
                .orElseThrow { EntityException("No student entity found!") }
    }

    fun findTeacher(id: Long): Teacher {
        return userRepository.findTeacher(id)
                .orElseThrow { EntityException("No teacher entity found!") }
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