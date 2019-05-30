package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception
    {
       oauthServer.checkTokenAccess("permitAll()");    
    }

    public void configure(ClientDetailsServiceConfigurer clients) {
        try {
			clients
			    .inMemory()
			        .withClient("first-client")
			        .secret(passwordEncoder().encode("noonewilleverguess"))
			        .scopes("resource:read")
			        .authorizedGrantTypes("authorization_code", "password")
			        .redirectUris("http://localhost:8081/oauth/login/client-app")
			        .and()
			        .withClient("resource-client")
			        .secret(passwordEncoder().encode("noonewilleverguess2"))
			        .scopes("resource:read")
			        .authorizedGrantTypes("authorization_code", "password")
					.and()	
					.withClient("automatic-client")
					.secret(passwordEncoder().encode("noonewilleverguess3"))
					.scopes("resource:read")
					.authorizedGrantTypes("password");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
       endpoints.authenticationManager(authenticationManager);
    }
}