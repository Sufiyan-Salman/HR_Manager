package HR_Manager.Configurations;

//package antifraud.Configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
//@RequiredArgsConstructor
// final walo ka cons banadega
public class ApplicationConfig {


    @Autowired
    CustomUserDetailService customUserDetailService;
//=============
//    private UserRepo userRepository;
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return username -> userRepository.findByEmail(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//
//        //agar ham new userdetaillsservice banate to hme loadusername ko override krna prnta hai , as we did in costumUserDetails service , but hamne lambda use kia to hmen ye nai krna prha
//    }
    //==============

//=========================
    //dusre triqe se kia hai , hamne custom user details ko autowire kia aur usko http basic me user details me dalidia , aur ye ese bbhi chal rha hai
// ise use krne klie ise aur uske neeche ko uncmment krenge aur phir authenticatin provider ko bhi uncomment krenge from security filter chain
//   basically ham yahan se ye kr k customUserDetails service pass krrhe hen auth provider ko , jab k filter me boot khud krrha h
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
////        authProvider.setUserDetailsService(userDetailsService());
//    authProvider.setUserDetailsService(customUserDetailService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    //==========================

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(13);
    }

}
