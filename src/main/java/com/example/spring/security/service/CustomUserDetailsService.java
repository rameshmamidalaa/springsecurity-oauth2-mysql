/**
 * 
 */
package com.example.spring.security.service;

import java.util.Arrays;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.spring.security.model.UserData;

/**
 * @author Ramesh_Mamidala
 *
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private final UserInformationService userInformationService;
	
	public CustomUserDetailsService(UserInformationService userInformationService) {
		this.userInformationService = userInformationService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserData userData = userInformationService.getUserDetailsByUserName(username);
		GrantedAuthority authority = new SimpleGrantedAuthority(userData.getRole());
				
		return new User(userData.getUserName(), userData.getPassword(), Arrays.asList(authority));
	}

}
