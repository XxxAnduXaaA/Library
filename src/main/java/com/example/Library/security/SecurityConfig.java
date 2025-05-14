package com.example.Library.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Configuration
@EnableJdbcHttpSession
public class SecurityConfig {


    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/register", "/auth/login")
                        .permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/books/**").authenticated()
                        .requestMatchers("/**").authenticated())
                .formLogin(form -> form.loginPage("/auth/login")
                        .usernameParameter("email")  // Теперь Spring ожидает "email" вместо "username"
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/auth/login?error=true")
                        .permitAll())
                .sessionManagement(session -> session
                        .maximumSessions(3)
                        .maxSessionsPreventsLogin(false))
                .sessionManagement(session -> session
                        .invalidSessionUrl("/auth/login?invalidSession=true")
                        .sessionFixation().migrateSession()
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(3))
                .build();
//        return http.csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // Разрешить доступ ко всему
//                .build();
    }

}
