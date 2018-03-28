package io.ermdev.classify.config

import org.springframework.boot.actuate.trace.http.HttpTrace
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*


@Configuration
@EnableResourceServer
@RestController
class ResourceServerConfig : ResourceServerConfigurerAdapter() {

    val messages = Collections.synchronizedList(LinkedList<Message>())

    override fun configure(http: HttpSecurity?) {
        http?.sessionManagement()
                ?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ?.and()
                ?.authorizeRequests()
                ?.antMatchers("/users")?.authenticated()
    }

    @GetMapping("/messages")
    fun getMessages(principal: HttpTrace.Principal): List<Message> {
        return messages
    }

    @PostMapping("/messages")
    fun postMessage(principal: HttpTrace.Principal, @RequestBody message: Message): Message {
        message.username = principal.getName()
        message.createdAt = LocalDateTime.now()
        messages.add(0, message)
        return message
    }

    class Message {
        var text: String? = null
        var username: String? = null
        var createdAt: LocalDateTime? = null
    }

}