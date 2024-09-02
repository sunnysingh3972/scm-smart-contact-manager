package com.smartcontactupgrade.smartcontact.confiq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.smartcontactupgrade.smartcontact.services.servicesimpl.SecurityCustomDetailService;

@Configuration
public class SecurityConfiq {

    // we use this olny for testing
    // it is used for only in memory
    // user create and login using java code with in memmory service
    // @Bean
    // public UserDetailsService userDetailsService(){
    // UserDetails user=User
    // .withDefaultPasswordEncoder()
    // .username("admin")
    // .password("admin")
    // .roles("ADMIN","USER")
    // .build();
    // return new InMemoryUserDetailsManager(user);

    // }

    @Autowired
    private OAutheticationSuccessHandler getAutheticationSuccessHandler;

    @Autowired
    private SecurityCustomDetailService getSecurityCustomDet;
    @Autowired
    private AuthfailureHandler getAuthfailureHandler;

    // configuration of authentication provider spring security
    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(getSecurityCustomDet);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // configuration
        // configuartion kiye h jo hme public chaiye ya protected chaiye
        httpSecurity.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/user/**").authenticated();
            auth.anyRequest().permitAll();
        });
        // form default login
        // agar hme kuch bhi change krna hua to yha ayege login related
        httpSecurity.formLogin(formLogin -> {
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            // formLogin.successForwardUrl("/user/dashboard");
            formLogin.defaultSuccessUrl("/user/profile");
            // apna login form get support kr rha h to hame ek post ka handlee bna skte h
            // formLogin.failureForwardUrl("/login?error=true");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
            // formLogin.failureHandler(new AuthenticationFailureHandler() {

            // @Override
            // public void onAuthenticationFailure(HttpServletRequest request,
            // HttpServletResponse response,
            // AuthenticationException exception) throws IOException, ServletException {
            // // TODO Auto-generated method stub
            // throw new UnsupportedOperationException("Unimplemented method
            // 'onAuthenticationFailure'");
            // }

            // });
            // formLogin.successHandler(new AuthenticationSuccessHandler() {

            // @Override
            // public void onAuthenticationSuccess(HttpServletRequest request,
            // HttpServletResponse response,
            // Authentication authentication) throws IOException, ServletException {
            // // TODO Auto-generated method stub
            // throw new UnsupportedOperationException("Unimplemented method
            // 'onAuthenticationSuccess'");
            // }

            // });

            formLogin.failureHandler(getAuthfailureHandler);

        });
        httpSecurity.rememberMe(rememberMe->rememberMe.key("uniqueAndSecret").tokenValiditySeconds(86400));
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.logout(formLogout -> {
            formLogout.logoutUrl("/do-logout");
            formLogout.logoutSuccessUrl("/login?logout=true");
        });
        // oauth configuration
        httpSecurity.oauth2Login(oauth -> {
            oauth.loginPage("/login");
            oauth.successHandler(getAutheticationSuccessHandler);
        });

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
