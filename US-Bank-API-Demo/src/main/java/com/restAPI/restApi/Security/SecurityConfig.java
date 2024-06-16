package com.restAPI.restApi.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public InMemoryUserDetailsManager userDetailsManager()
    {   // Users who are allowed to access the API

        UserDetails apoorv = User.builder()
                                 .username("apoorv")
                                 .password("{noop}Qwerty123")
                                 .roles("Student")
                                 .build();

         UserDetails anand = User.builder()
                                 .username("anand")
                                 .password("{noop}Qwerty124")
                                 .roles("Student","Coordinator")
                                 .build();

         UserDetails reena = User.builder()
                                 .username("reena")
                                 .password("{noop}Qwerty125")
                                 .roles("Student","Coordinator","Admin")
                                 .build();

        return new InMemoryUserDetailsManager(apoorv,anand,reena);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{

        http.authorizeHttpRequests(configurer ->
                                   configurer.requestMatchers(HttpMethod.GET,"/graphql").hasRole("Student")
                                             .requestMatchers(HttpMethod.GET,"/graphql").hasRole("Student")
                                             .requestMatchers(HttpMethod.POST,"/graphql").hasRole("Coordinator")
                                             .requestMatchers(HttpMethod.PUT,"/graphql").hasRole("Coordinator")
                                             .requestMatchers(HttpMethod.PATCH,"/graphql").hasRole("Coordinator")
                                             .requestMatchers(HttpMethod.DELETE,"/graphql").hasRole("Admin")
        );

        http.httpBasic(Customizer.withDefaults());

        http.csrf(crsf -> crsf.disable());

        return http.build();
    }
}
