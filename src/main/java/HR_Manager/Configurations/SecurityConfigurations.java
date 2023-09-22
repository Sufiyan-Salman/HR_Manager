package HR_Manager.Configurations;

//import lombok.RequiredArgsConstructor;

//import antifraud.CustomHandlers.CustomAccessDeniedHandler;
import HR_Manager.Jwt.JwtAuthenticationFilter;
import HR_Manager.Jwt.JwtAuthenticationFilterr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
//this one is better
@Configuration
@EnableWebSecurity()
@EnableMethodSecurity(prePostEnabled = true)// before this test 11 , now 3

//@EnableWebSecurity  //(prepostEnabled=true ) , jab hemen ye krna hia to jis controller pe ye laga a hai wahan preauthrorize lagate hen as written in line 55

//@RequiredArgsConstructor
public class SecurityConfigurations {

    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private JwtAuthenticationFilterr jwtAuthFilter;
//    private JwtAuthenticationFilter jwtAuthFilter;

//    @Autowired
//    CustomAccessDeniedHandler customAccessDeniedHandler;


//    @Autowired
//    private final JwtAuthenticationFilter jwtAuthFilter;


//    @Autowired//ye na lagao to masla krrha tha
//    private AuthenticationProvider authenticationProvider;

    //    @Autowired
//    RestAuthenticationEntryPoint restAuthenticationEntryPoint;//// reason neeche haai
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http//.
//        httpBasic() // if we use this , to token k sath http basic auth bhi chalega , that is the reason we have disabled it
//                .authenticationEntryPoint(restAuthenticationEntryPoint)
                // jo mene new exceptions banae theen , ye use use krne klie mene dala tha , lekin comment out krne k baad b chal rha hai se bhi chal rha hai , aur iski zarurt prh rhi
//                .and()
                .csrf()
                .disable().headers().frameOptions().disable() // for Postman, the H2 console
                .and()
//                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
//                .and()
                .authorizeHttpRequests()
                //ye jo controller hai m ye open for all hai
//                .requestMatchers("/**")
                .requestMatchers(HttpMethod.POST,"/Employee/signup","/Employee/login","/h2", "/actuator/shutdown","/error","/hello" ).permitAll()
                .requestMatchers("/hello","/error" ).permitAll()
//                .requestMatchers(toH2Console())
//                .permitAll()
                //sirf in url ko permit krega for outsiders
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //hamne stateless isliye rkha take server kuch b na rkhe aur har baar token ko validate  , means new session for each request
                .and()
//                .userDetailsService(customUserDetailService)
                .addFilterBefore(jwtAuthFilter,  UsernamePasswordAuthenticationFilter.class); // srif is se bhi chal rha hai , is ke zariye application config me kuch b nai krna  prh rha hai
//                .authenticationProvider(authenticationProvider) ;// application config me hai , is se bhi chalega lekin phir config me jake sab uncomment krna prega



        return http.build();
    }

    //this is the way we write ths in latest spring security version
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http.csrf(AbstractHttpConfigurer::disable)
////        http.csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth ->  auth .requestMatchers(HttpMethod.POST,"/Employee/signup","/Employee/login","/h2", "/actuator/shutdown","/error","/hello" ).permitAll()
//                        .requestMatchers("/hello","/error" ).permitAll()
//                .anyRequest()
//                .authenticated())
////                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }
}
