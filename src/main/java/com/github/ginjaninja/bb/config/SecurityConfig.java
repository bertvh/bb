package com.github.ginjaninja.bb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;

import com.github.ginjaninja.bb.auth.AccountUserDetailsService;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = AccountUserDetailsService.class, includeFilters = @Filter(Controller.class), useDefaultFilters = false)
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;
	
    /**
     * Configure permissions for urls/directories
     */
    @Override
	protected void configure(HttpSecurity http) throws Exception {
	  http.authorizeRequests()
		.anyRequest().permitAll();
	}
    
    @Autowired
    public void registerAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService);
    }
}
