package io.ermdev.classify.config

import io.ermdev.classify.util.web.interceptor.BasicSecurityInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.beans.factory.annotation.Autowired



@Configuration
class WebMvcConfig : WebMvcConfigurer {

    private lateinit var basicSecurityInterceptor: BasicSecurityInterceptor

    @Autowired
    fun setLoginInterceptor(basicSecurityInterceptor: BasicSecurityInterceptor) {
        this.basicSecurityInterceptor = basicSecurityInterceptor
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(basicSecurityInterceptor).addPathPatterns("/**")
    }
}