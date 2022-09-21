package com.example.citylist.config;

import com.example.citylist.auth.config.CustomAuthenticationSuccessHandler;
import com.example.citylist.auth.service.impl.CustomUserDetailsService;
import com.example.citylist.constant.ApiPathConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

import static java.lang.String.format;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

//        httpSecurity.cors().and().csrf().disable()
//                .authorizeRequests().antMatchers(
//                        "/**").permitAll();

        httpSecurity.cors().and().csrf().disable()
                .authorizeRequests().antMatchers(
                        "/",
                        "/error",
                        "/*.js",
                        "/*.css",
                        "/**/*.png",
                        "/**/*.css",
                        "/login"
                ).permitAll()
                .anyRequest().authenticated().and()
                .formLogin()
                .usernameParameter("username").successHandler( new CustomAuthenticationSuccessHandler() )
                .permitAll().and()
                .logout().logoutSuccessUrl("/").permitAll();

        httpSecurity.authenticationProvider( authenticationProvider() );
        return httpSecurity.build();

    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        //config.addAllowedOrigin("*");
        config.setAllowedOrigins( List.of("http://localhost:4200"));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
