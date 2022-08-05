package com.edo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.edo.user.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.formLogin()
        .loginPage("/members/login")
        .defaultSuccessUrl("/")
        .usernameParameter("email")
        .failureUrl("/members/login/error")
        .and()
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
        .logoutSuccessUrl("/")
        ;


http.authorizeRequests() // 시큐리티 처리에 HttpServletRequests를 이용
        .mvcMatchers("/","/members/**","/item/**","/images/**").permitAll() // 모든 사용자가 해당 경로에 접근 가능하게
        .mvcMatchers("/admin/**").hasRole("ADMIN") // admin인 경우 ADMIN만
        .anyRequest().authenticated(); // 위 설정 제외한 경로들은 모두 인증을 요구하도록 설정

    http.exceptionHandling()
            .authenticationEntryPoint(new CustomAuthenticationEntryPoint()); // 인증되지 않은 사용자인 경우 핸들러를 등록
    }

}
