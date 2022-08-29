package com.edo.user.entity;

import javax.persistence.*;

import com.edo.constant.Role;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.edo.user.dto.UserDto;

@Getter
@Setter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
//회원
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

	private String Uemail;

    //비밀번호
    private String password;

    //닉네임(Unique)
    @Column(unique = true)
	private String nickname;

    //이름
    private String name;

    //핸드폰
    private int phone;

    //대표이미지
    //image url 주소를 넣을예정
	private String imageURL;

    public static User createUser(UserDto userDto, PasswordEncoder passwordEncoder){
        User user = new User();
        user.setName(userDto.getName());
        user.setUemail(userDto.getEmail());
        user.setNickname(userDto.getNickname());
        user.setPhone(userDto.getPhone());
        String passwrod = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(passwrod);
        return user;
    }
}
