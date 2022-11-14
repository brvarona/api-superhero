package com.brvarona.superhero.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("admin")
			.password("{noop}admin")
			.authorities("ROLE_ADMIN");
	}
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        	.csrf().disable()
        	.headers().disable()
        	.authorizeHttpRequests()
        	.antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
                    "/configuration/security", "/swagger-ui.html", "/webjars/**").permitAll()
            .antMatchers(HttpMethod.GET, "/api/**/superheros*/**").permitAll()
            .antMatchers(HttpMethod.DELETE, "/api/**/superheros/cache").permitAll()
        	.anyRequest().authenticated()
        	.and().httpBasic().and().sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        return http.build();
    }

}