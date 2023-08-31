package com.example.netwitch.configurations;

import com.example.netwitch.services.CustomUserDetailsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;

    @SneakyThrows
    @Bean
    @Transactional
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http.authenticationProvider(authenticationProvider());
        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                )
//                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/registration", "/static/**", "/css/**", "/api/stream/*", "/category/*", "/live/*", "/profile/*").permitAll()
                        .requestMatchers("/donate", "/application", "/subscribe/*", "/unsubscribe/*", "/updateBalance")
                        .hasAnyAuthority("ROLE_USER")
                        .requestMatchers("/start", "/end/*" )
                        .hasAnyAuthority("ROLE_STREAMER")
                        .requestMatchers("/admin/**", "/applicationList", "/approve", "/reject" )
                        .hasAnyAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(logout -> logout.logoutSuccessUrl("/").permitAll());
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

}
