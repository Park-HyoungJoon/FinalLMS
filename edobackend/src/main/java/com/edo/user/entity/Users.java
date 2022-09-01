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
@Builder
//회원
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long id;

    @Column(length = 255)
	private String usersEmail;

    //비밀번호
    @Column(length = 255)
    private String password;

    //닉네임(Unique)
    @Column(unique = true , length = 10)
	private String nickname;

    //이름
    @Column(length = 60)
    private String name;

    //핸드폰
    @Column(length = 15)
    private int phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    //대표이미지
    //image url 주소를 넣을예정
    @Column(columnDefinition = "LONGTEXT")
	private String imageUrl;

    public static Users createUser(UserDto userDto, PasswordEncoder passwordEncoder){
        Users users = new Users();
        users.setName(userDto.getName());
        users.setNickname(userDto.getNickname());
        users.setPhone(userDto.getPhone());
        String passwrod = passwordEncoder.encode(userDto.getPassword());
        users.setPassword(passwrod);
        return users;
    }
}