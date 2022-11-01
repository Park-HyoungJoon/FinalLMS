package com.edo.user.dto;

import com.edo.user.entity.Member;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDto {

	@NotBlank(message = "이름은 필수 입력 값입니다.")
	private String memberName;

	@NotEmpty(message = "이메일은 필수 입력 값입니다.")
	private String memberEmail;

	@NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
	@Length(min = 8, max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
	private String memberPassword;

	@NotNull(message = "핸드폰 번호는 필수 입력 값입니다.")
	private String memberPhone;

	@NotEmpty(message = "닉네임은 필수 입력 값입니다.")
	private String memberNickname;

//	role이 안 들어감...
//	@NotEmpty
//	private Role userRole;

	private static ModelMapper modelMapper = new ModelMapper();

	public Member createUsers(){
		return modelMapper.map(this, Member.class);
	}

	public static MemberDto of(Member member){
		return modelMapper.map(member, MemberDto.class);
	}
}
