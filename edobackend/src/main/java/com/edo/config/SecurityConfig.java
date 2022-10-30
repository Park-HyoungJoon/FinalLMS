package com.edo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.edo.user.handler.LoginFailHandler;
import com.edo.user.service.MemberService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    MemberService memberService;

    // 원래는 별개지만 지금 체이닝을 걸어놓은것.
    // http 요청에 대한 보안 설정. 페이지 권한, 로그인 페이지, 로그아웃 메소드 설정 예정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.formLogin()
                .loginPage("/member/login") 					//로그인 페이지  url 설정
                .defaultSuccessUrl("/")					//로그인 성공 시 이동할 url
                .usernameParameter("usersEmail")		//로그인 시 사용할 파라미터 이름으로 email 을 지정
                .passwordParameter("usersPassword") 	//view단에서 plain text로 받아온 걸 encode해서 db랑 매칭

                .failureHandler(loginFailHandler())		//로그인 실패 시 처리하는 핸들러 등록.
                //.failureUrl("/login/error")//실패 시 이동할 url

                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/"); //로그아웃 성공 시 연결될 화면

        http.authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/img/**","/image/**","/images/**","/file/**").permitAll()
                .antMatchers("/", "/member/**", "/lecture/**","/test2", "/community/*","/contents/**").permitAll()
                .antMatchers("/community/write").hasAnyRole()
                .anyRequest()
                .authenticated() //아직 로그인 완전히 구현 안 됐기 때문에 일단 비활성화
                .and()
                .httpBasic(); 
        
        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()); //인증되지 않은 사용자 접근 시 수행
        http.headers().frameOptions().sameOrigin();
        

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LoginFailHandler loginFailHandler(){

        return new LoginFailHandler();
    }
}
