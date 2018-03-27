package io.ermdev.classify.interceptor

import com.sun.org.apache.xml.internal.security.utils.Base64
import io.ermdev.classify.data.service.UserService
import io.ermdev.classify.util.Error
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class BasicSecurityInterceptor(val userService: UserService) : HandlerInterceptorAdapter() {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        try {
            val auth: List<String>? = request.getHeader("Authorization")?.split(" ")
            if (auth != null && auth.size == 2 && !StringUtils.isEmpty(auth[0]) && !StringUtils.isEmpty(auth[1])) {
                val token = String(Base64.decode(auth[1]))
                val username: String? = token.split(":")[0]
                val password: String? = token.split(":")[1]
                if (userService.findByUsername(username ?: "").password == password) {
                    return true
                } else {
                    throw RuntimeException("Access is denied due to invalid credentials")
                }
            } else {
                throw RuntimeException("Full authentication is required to access this resource")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            val error = Error(status = 401, error = HttpStatus.UNAUTHORIZED.name, message = e.message ?: "")
            response.contentType = "application/json"
            response.characterEncoding = "utf-8"
            response.status = 401
            response.writer.print(error.json())
            return false
        }
    }
}