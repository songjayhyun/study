package com.example.study.config;

import com.example.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@SuppressWarnings("unused")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("testid")
                .password(passwordEncoder().encode("testPassword"))
                .roles("USER"); // role 여러 개 가능

        auth.userDetailsService(userService);

    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/test/**").hasRole("ADMIN") // 또는 permitAll
                .antMatchers("/api/**").authenticated()
                //.antMatchers("/api/**").hasRole("USER")
                .anyRequest().permitAll()
                // antMathcers if anyRequest else
                .and()
                .formLogin()
                .and()
                .logout()
                .logoutUrl("/api/v1/users/signout");

    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    @SuppressWarnings("unused")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}