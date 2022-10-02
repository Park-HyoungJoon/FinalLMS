package com.edo.user.dto;

import com.edo.user.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	@NotBlank(message = "이름은 필수 입력 값입니다.")
	private String usersName;
	@NotEmpty(message = "이메일은 필수 입력 값입니다.")
	private String usersEmail;
	@NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
	private String usersPassword;
	@NotNull(message = "핸드폰 번호는 필수 입력 값입니다.")
	private int usersPhone;
	@NotEmpty(message = "닉네임은 필수 입력 값입니다.")
	private String usersNickname;

	private static ModelMapper modelMapper = new ModelMapper();

	public Users createUserDto(){
		return modelMapper.map(this, Users.class);
	}

	public static UserDto of(Users users){
		return modelMapper.map(users, UserDto.class);
	}
}
