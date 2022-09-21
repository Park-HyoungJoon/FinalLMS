package com.edo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//exclude = { SecurityAutoConfiguration.class }는 security 기본 로그인 화면 안 나오게 하는 설정
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class EdobackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdobackendApplication.class, args);
	}

}
