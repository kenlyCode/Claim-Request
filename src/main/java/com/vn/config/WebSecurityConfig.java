package com.vn.config;

import com.vn.auth.CustomStaffDetailService;
import com.vn.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    public CustomStaffDetailService customStaffDetailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((authorize) -> authorize

                        .requestMatchers("/", "/index", "/home", "/img/**", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/admin/**", "/api/staff/*/admin", "/api/staff/admin").hasAnyAuthority(Role.ROLE_ADMIN.name())
                        .requestMatchers("/approver/**", "/api/claim/*/approver", "/api/claim/*/*/*/approver").hasAnyAuthority(Role.ROLE_APPROVER.name())
                        .requestMatchers("/finance/**", "/api/claim/*/finance", "/api/claim/*/*/*/finance").hasAnyAuthority(Role.ROLE_FINANCE.name())
                        .requestMatchers("/employee/**", "/api/claim/*/employee", "/api/staff/*/employee", "/api/claim", "api/claim/employee").hasAnyAuthority(Role.ROLE_EMPLOYEE.name()).anyRequest().authenticated()
                )
                .formLogin(form -> form

                                .loginPage("/login")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .loginProcessingUrl("/login-check")
                                .defaultSuccessUrl("/home")
//                                .successHandler(authenticationSuccessHandler())
                                .permitAll()

                )
                .logout(logout -> logout

                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .logoutSuccessUrl("/login")

                )

                .csrf(csrf -> csrf.disable());

        return http.build();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customStaffDetailService).passwordEncoder(passwordEncoder);
    }

}
