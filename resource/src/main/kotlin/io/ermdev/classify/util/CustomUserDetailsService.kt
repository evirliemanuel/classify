//package io.ermdev.classify.util
//
//import io.ermdev.classify.data.entity.Role
//import io.ermdev.classify.data.service.UserService
//import io.ermdev.classify.exception.EntityException
//import org.springframework.security.core.GrantedAuthority
//import org.springframework.security.core.authority.SimpleGrantedAuthority
//import org.springframework.security.core.userdetails.User
//import org.springframework.security.core.userdetails.UserDetails
//import org.springframework.security.core.userdetails.UserDetailsService
//import org.springframework.security.core.userdetails.UsernameNotFoundException
//import org.springframework.stereotype.Component
//import java.util.*
//
//@Component
//class CustomUserDetailsService(val userService: UserService) : UserDetailsService {
//
//    override fun loadUserByUsername(username: String?): UserDetails {
//        try {
//            val enabled = true
//            val accountNonExpired = true
//            val credentialsNonExpired = true
//            val accountNonLocked = true
//            val user = userService.findByUsername(username ?: "")
//
//            return User(user.username, "{noop}${user.password}", enabled, accountNonExpired, credentialsNonExpired,
//                    accountNonLocked, grantedAuthorities(user.roles))
//        } catch (e: EntityException) {
//            throw UsernameNotFoundException(e.message)
//        }
//    }
//
//    private fun grantedAuthorities(roleList: Collection<Role>): Collection<GrantedAuthority> {
//        val grantedAuthorities = HashSet<GrantedAuthority>()
//        roleList.parallelStream().forEach { role -> grantedAuthorities.add(SimpleGrantedAuthority(role.name)) }
//        return grantedAuthorities
//    }
//}