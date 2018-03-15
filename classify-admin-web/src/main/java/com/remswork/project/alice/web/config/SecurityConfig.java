package com.remswork.project.alice.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.remswork.project.alice.web.service.UserDetailsServiceImpl;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/test/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/favicon.ico").permitAll()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/js/**").permitAll()
				.antMatchers("/static/**").permitAll()
				.antMatchers("/image/**").permitAll()
				.antMatchers("/data/**").permitAll()
				.antMatchers("/less/**").permitAll()
				.antMatchers("/blog/**").permitAll()
				.antMatchers("/vendor/**").permitAll()
				.antMatchers("/dist/**").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.usernameParameter("username")
				.passwordParameter("password")
				.loginPage("/login")
				.successForwardUrl("/login/success")
				.permitAll()
				.and()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login")
				.permitAll()
				.and()
			.csrf()
				.disable();
	}
}
