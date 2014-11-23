package com.github.ginjaninja.bb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring()
                .antMatchers("/resources/**");
    }
    
    @Override
	protected void configure(HttpSecurity http) throws Exception {
 
	  http
		.authorizeRequests() // if a request is not matched by any other WebSecurityConfigurerAdapter that has a @Order of less than this one
        	.antMatchers("/resources/**","/").permitAll()
            .anyRequest().authenticated() // it requires authentication
            .and() // and
         .formLogin() // use form based login for authentication
         	.loginPage("/login") // login page is available at /login
            .permitAll() // allow everybody to access the login page
            ;
	}
}
