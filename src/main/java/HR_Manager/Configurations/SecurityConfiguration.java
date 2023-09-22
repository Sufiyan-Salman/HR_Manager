//package HR_Manager.Configurations;
//
//
//
////package com.java.springclasses.jwt.config;
//
//import HR_Manager.Jwt.JwtAuthenticationFilter;
////import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.authentication.logout.LogoutHandler;
//
//@Configuration
//@EnableWebSecurity
////@RequiredArgsConstructor
//public class SecurityConfiguration {
//
//    // isme autowire nai lagaya
//    @Autowired
//    private JwtAuthenticationFilter jwtAuthFilter;
////    private final AuthenticationProvider authenticationProvider;
////  private final LogoutHandler logoutHandler;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.httpBasic().and()
//                .csrf()
//                .disable()
//                .authorizeHttpRequests()
//                //ye jo controller hai m ye open for all hai
//                .requestMatchers("/Employee/signup/**","/**")//yahan pe abhi b masla hai , seat ka jo endpint hai , ye isko b verify krrha hai hai , even though mene ise permit kia hua hai yahan se
//                .permitAll()
////            .requestMatchers("/api/v1/demo-controller").permitAll()
////            .requestMatchers("/api/v1/auth/register").permitAll()
//
//                .anyRequest()
//                .authenticated()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                //hamne stateless isliye rkha take server kuch b na rkhe aur har baar token ko validate  , means new session for each request
//                .and()
//
////                .authenticationProvider(authenticationProvider) // application config me hai
//                .addFilterBefore(jwtAuthFilter,  UsernamePasswordAuthenticationFilter.class)
////        .logout()     // ye charon jab implement krunga jab smjh jaunga
////        .logoutUrl("/api/v1/auth/logout")
////        .addLogoutHandler(logoutHandler)
////        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
//        ;
//
//        return http.build();
//    }
//}
