package com.example.demo.club.security;

import com.example.demo.club.security.jwt.JwtTokenProvider;
import com.example.demo.club.security.jwt.JwtAccessDeniedHandler;
import com.example.demo.club.security.jwt.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
    private final CustomUserDetailService customUserDetailService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() throws Exception {
       return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

   /*@Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        SavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler =
                new SavedRequestAwareAuthenticationSuccessHandler();
        authenticationSuccessHandler.setDefaultTargetUrl("/member/main");

        return authenticationSuccessHandler;
    }*/

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(customUserDetailService);
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable();
        //
        http.authorizeRequests()
            .antMatchers("/login").permitAll() // 인증없이 접근을 허용
            .antMatchers("/logout").permitAll() // 인증없이 접근을 허용
            .anyRequest().authenticated(); // 요청들에 대한 접근제한을 설정
        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션을 사용하지 않음
        // exception handling for jwt
        http.exceptionHandling()
            .accessDeniedHandler(jwtAccessDeniedHandler)    // 권한 부족 예외에 대한 처리를 위한 핸들러를 설정하는 메서드
            .authenticationEntryPoint(jwtAuthenticationEntryPoint); // 인증 실패 예외에 대한 처리를 위한 핸들러를 설정하는 메서드
        // Apply JWT
        http.apply(new JwtSecurityConfig(jwtTokenProvider));  // JwtSecurityConfig 클래스에서 정의한 JWT 인증 방식 설정을 HttpSecurity 객체에 적용
        /*http.authorizeHttpRequests()
                .mvcMatchers("/login").permitAll()
                .mvcMatchers("/member/**").hasRole("USER")
                .anyRequest().authenticated();
        http.formLogin()
                .loginPage("/login")
                .usernameParameter("memberId")
                .passwordParameter("password")
                .successHandler(authenticationSuccessHandler())
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/login");
        http.httpBasic();*/

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder  getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
