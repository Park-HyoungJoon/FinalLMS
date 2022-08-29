package com.edo.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
	@NotNull(message = "핸드폰 번호는 필수 입력 값입니다.")
	private int phone;
	@NotEmpty(message = "닉네임은 필수 입력 값입니다.")
	private String nickname;
}
