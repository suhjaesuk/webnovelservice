package com.numble.webnovelservice.util.security.config;

import com.numble.webnovelservice.util.jwt.JwtAuthenticationFilter;
import com.numble.webnovelservice.util.jwt.JwtUtil;
import com.numble.webnovelservice.util.security.exception.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.csrf().disable()
                .cors().configurationSource(corsConfigurationSource());

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().
                antMatchers("/api/auth/**").permitAll().
                antMatchers("/api/novels/all").permitAll().
                antMatchers("/api/novels/**/details").permitAll().
                antMatchers("/api/novels/latest").permitAll().
                antMatchers("/api/novels/genre/**").permitAll().
                antMatchers("/api/novels/search/**").permitAll().
                antMatchers("/api/daily-bests/all-types").permitAll().
                antMatchers("/api/daily-bests/paid").permitAll().
                antMatchers("/api/daily-bests/free").permitAll().
                antMatchers("/api/home-exposures").permitAll().
                anyRequest().authenticated().
                and().
                addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class).
                exceptionHandling().
                authenticationEntryPoint(new CustomAuthenticationEntryPoint());

        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        configuration.addExposedHeader("AccessToken");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}