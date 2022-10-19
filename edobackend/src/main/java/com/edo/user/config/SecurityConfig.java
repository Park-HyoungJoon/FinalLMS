package com.edo.user.config;

import com.edo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    UserService userService;

//    원래는 별개지만 지금 체이닝을 걸어놓은것.
//    http 요청에 대한 보안 설정. 페이지 권한, 로그인 페이지, 로그아웃 메소드 설정 예정
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.formLogin()
                .loginPage("/login") //로그인 페이지  url 설정
                .defaultSuccessUrl("/")//로그인 성공 시 이동할 url
                .usernameParameter("usersEmail")//로그인 시 사용할 파라미터 이름으로 email 을 지정
                .failureUrl("/login")//실패 시 이동할 url
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                .logoutSuccessUrl("/"); //로그아웃 성공 시 연결될 화면

        http.authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/img/**").permitAll()
                .antMatchers("/","/login","/memberjoin","/memberjoinInfo").permitAll();
//                .anyRequest().authenticated();

//        http.exceptionHandling()
//                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) //인증되지 않은 사용자 접근 시 수행
//        ;


        return http.build();


    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
