package io.ermdev.classify.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableOAuth2Sso
public class OAuth2Config extends WebSecurityConfigurerAdapter {


}
