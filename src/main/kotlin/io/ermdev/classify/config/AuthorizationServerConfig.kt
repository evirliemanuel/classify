package io.ermdev.classify.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer

@EnableAuthorizationServer
@Configuration
class AuthorizationServerConfig(val authenticationManager: AuthenticationManager) : AuthorizationServerConfigurerAdapter() {

    override fun configure(security: AuthorizationServerSecurityConfigurer?) {
        security?.tokenKeyAccess("permitAll()")
                ?.checkTokenAccess("isAuthenticated")
    }

    override fun configure(clients: ClientDetailsServiceConfigurer?) {
        clients?.inMemory()
                ?.withClient("ClientId")
                ?.secret("secret")
                ?.authorizedGrantTypes("authorization_code")
                ?.scopes("user_info")
                ?.autoApprove(true)

    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer?) {
        endpoints?.authenticationManager(authenticationManager)
    }
}