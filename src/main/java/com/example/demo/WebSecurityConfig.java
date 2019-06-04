package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Value("${spring.security.oauth2.client.registration.first-client.index-uri = http://localhost:4200/}")
	private String firstClientRedirectUri;
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
	   return super.authenticationManagerBean();
	}
	
	/*@Override
    public void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests()
	      .anyRequest().authenticated()
	      .and().httpBasic();
		//http.logout().permitAll();
		//http.requestMatcher()
        /*http
            .requestMatcher(resources()).authorizeRequests()
            .anyRequest().authenticated();*/
   /* }*/
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		super.configure(http);
		http.logout().logoutSuccessUrl(firstClientRedirectUri);
		//http.logout().logoutSuccessUrl("localhostdgfsa");
	  /*http.antMatcher("/**")                                       
	    .authorizeRequests()
	      .antMatchers("/", "/login**", "/webjars/**").permitAll()                           
	    .and().exceptionHandling();
	      //.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"));*/
	}
	
	
	
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
            User.withDefaultPasswordEncoder()
                .username("enduser")
                .password("password")
                .roles("USER")
                .build(),
               User.withDefaultPasswordEncoder()
                .username("enduser2")
                .password("password2")
                .roles("USER")
                .build());
    }
}
