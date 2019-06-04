package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${spring.security.oauth2.client.registration.first-client.redirect-uri}")
	private String firstClientRedirectUri;
	
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
			        .redirectUris(firstClientRedirectUri)
			        .autoApprove(true)
			        .and()
			        .withClient("resource-client")
			        .secret(passwordEncoder().encode("noonewilleverguess2"))
			        .scopes("resource:read")
			        .authorizedGrantTypes("authorization_code", "password")
					.and()	
					.withClient("automatic-client")
					.secret(passwordEncoder().encode("noonewilleverguess3"))
					.scopes("resource:read")
					.authorizedGrantTypes("password", "client_credentials");
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