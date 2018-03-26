package io.ermdev.classify.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer

@EnableResourceServer
@Configuration
class ResourceServerConfig(val authenticationManager: AuthenticationManager) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http?.authorizeRequests()
                ?.anyRequest()
                ?.authenticated()
                ?.and()
            ?.formLogin()
            ?.permitAll()
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.parentAuthenticationManager(authenticationManager)
                ?.inMemoryAuthentication()
                    ?.withUser("rafaelmanuel")
                    ?.password("123")
                    ?.roles("ADMIN")
    }
}