package com.github.ginjaninja.bb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.github.ginjaninja.bb.auth.AccountUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private AccountUserDetailsService userDetailsService;
	
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService);
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
	  	.csrf().disable()
		.authorizeRequests() // if a request is not matched by any other WebSecurityConfigurerAdapter that has a @Order of less than this one
        	.antMatchers("/resources/**","/").permitAll()
            .anyRequest().authenticated() // it requires authentication
            .and() // and
         .formLogin() // use form based login for authentication
         	.loginPage("/login/form") // login page is available at /login
            .loginProcessingUrl("/login")
            .failureUrl("/login/form?error")
         	.permitAll() // allow everybody to access the login page
            ;
	}
}
