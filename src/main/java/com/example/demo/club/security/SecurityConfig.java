package com.example.demo.club.security;

import com.example.demo.club.security.jwt.JwtTokenProvider;
import com.example.demo.club.security.jwt.JwtAccessDeniedHandler;
import com.example.demo.club.security.jwt.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.security.web.SecurityFilterChain;

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

    /**
     *  정적 리소스에 대한 보안을 설정(이미지, 자바스크립트, CSS 등)
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() throws Exception {
       return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    /**
     *  인증에 사용될 DaoAuthenticationProvider를 반환
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(customUserDetailService);
        return daoAuthenticationProvider;
    }

    /**
     *  비밀번호 암호화
     */
    @Bean
    public BCryptPasswordEncoder  getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 인증없이 접근을 허용
                .antMatchers("/login").permitAll()
                .antMatchers("/logout").permitAll()
                // 요청들에 대한 접근제한을 설정
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                // 권한 부족 예외에 대한 처리를 위한 핸들러를 설정하는 메서드
                .accessDeniedHandler(jwtAccessDeniedHandler)
                // 인증 실패 예외에 대한 처리를 위한 핸들러를 설정하는 메서드
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()// Apply JWT
                .apply(new JwtSecurityConfig(jwtTokenProvider));  // JwtSecurityConfig 클래스에서 정의한 JWT 인증 방식 설정을 HttpSecurity 객체에 적용

        return http.build();
    }
}
