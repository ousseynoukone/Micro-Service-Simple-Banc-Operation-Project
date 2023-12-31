package com.rezilux.auth_services.Config ;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    UserAuthProvider userAuthProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        try {
            http.csrf(AbstractHttpConfigurer::disable)
                    .addFilterBefore(new JwAuthFilter(userAuthProvider), BasicAuthenticationFilter.class)
                    .sessionManagement(customizer ->customizer.
                            sessionCreationPolicy( SessionCreationPolicy.STATELESS)).authorizeHttpRequests((requests)->requests.
                            requestMatchers(HttpMethod.POST,"/auth/login","/auth/validateToken","/auth/register").permitAll().anyRequest().authenticated());

            return  http.build();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
