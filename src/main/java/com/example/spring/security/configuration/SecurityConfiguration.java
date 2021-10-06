/**
 * 
 */
package com.example.spring.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.spring.security.service.CustomUserDetailsService;

/**
 * @author Ramesh_Mamidala
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final CustomUserDetailsService customUserDetailsService;

	@Autowired
	public SecurityConfiguration(CustomUserDetailsService customUserDetailsService) {
		this.customUserDetailsService = customUserDetailsService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.authorizeRequests()
				.antMatchers("/oauth/token")
				.permitAll()
				.antMatchers(HttpMethod.GET, "/users")
				.hasRole("READ")
				.anyRequest()
				.authenticated();
 
		// @formatter:on

	}

	@Bean
	public DaoAuthenticationProvider useDaoauthenticationProvider() {

		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(useBCryptPasswordEncoder());

		return daoAuthenticationProvider;

	}

	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(useDaoauthenticationProvider());
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public BCryptPasswordEncoder useBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
    @Override
    public void configure(WebSecurity web) throws Exception {
    	// @formatter:off
        web
        	.ignoring()
        	.antMatchers( "/static/**","/resources/**", "/js/**", "/css/**", "/images/**");
       // @formatter:on
    }
	
}
