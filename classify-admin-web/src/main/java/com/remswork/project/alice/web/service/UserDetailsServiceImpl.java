package com.remswork.project.alice.web.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.remswork.project.alice.model.UserDetail;
import com.remswork.project.alice.web.service.exception.UserDetailServiceException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDetailServiceImpl userDetailService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDetail userDetail = null;
		try {
			for(UserDetail u : userDetailService.getUserDetailList()) {
				if(!u.getUserType().equals(UserDetail.USER_ADMIN))
					continue;
				if(username.equals(u.getUsername()))
					userDetail = u;
			}
		} catch (UserDetailServiceException e) {
			e.printStackTrace();
		}
		
		if(userDetail == null)
			throw new UsernameNotFoundException("not found");
			
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        return new org.springframework.security.core.userdetails.User(
        		userDetail.getUsername(), userDetail.getPassword(), grantedAuthorities);
	}
}
