package com.edo.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

	@NotBlank(message = "이름은 필수 입력 값입니다.")
	private String name;
	@NotEmpty(message = "이메일은 필수 입력 값입니다.")
	private String email;
	@NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
	private String password;
	@NotEmpty(message = "주소는 필수 입력 값입니다.")
	private String address;
}
