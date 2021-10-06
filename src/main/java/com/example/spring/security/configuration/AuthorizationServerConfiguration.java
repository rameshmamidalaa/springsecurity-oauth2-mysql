/**
 * 
 */
package com.example.spring.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * The Resource Owner Password Flow.
 * 
 * @author Ramesh_Mamidala
 *
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;

	@Autowired
	public AuthorizationServerConfiguration(
			@Qualifier("authenticationManagerBean") AuthenticationManager authenticationManager,
			UserDetailsService userDetailsService) {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

		// @formatter:off
		security
			.tokenKeyAccess("permitAll()")
			.checkTokenAccess("isAuthenticated()");
		// @formatter:on
	}

	/**
	 * Client configuration that allows the password grant.
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		// @formatter:off
		clients.inMemory()
			.withClient("my_client_id")
				.secret("{noop}secret")
				.authorizedGrantTypes("password", "authorization_code", "refresh_token")
				.scopes("read", "write")
				.authorities("WRITE", "OPERATIONS", "READ","ADMIN")
				.autoApprove(true)
				.accessTokenValiditySeconds(180)
				.refreshTokenValiditySeconds(600);
		// @formatter:on
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		// @formatter:off
		endpoints
			.tokenStore(tokenStore())
			.authenticationManager(authenticationManager)
			.accessTokenConverter(defaultAccessTokenConverter())
			.userDetailsService(userDetailsService);
		// @formatter:on
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(defaultAccessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter defaultAccessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setSigningKey("123");
		return jwtAccessTokenConverter;
	}

}
