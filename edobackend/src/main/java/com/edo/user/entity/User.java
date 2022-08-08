package com.edo.user.entity;

import javax.persistence.*;

import com.edo.constant.Role;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.edo.user.dto.UserDto;

@Getter
@Setter
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User {

	@Id
	@Column(name="userId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	@Column(unique = true)
	private String email;
	
	private String password;
	
	private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static User createUser(UserDto userDto, PasswordEncoder passwordEncoder){
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAddress(userDto.getAddress());
        String passwrod = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(passwrod);
        user.setRole(Role.ROLE_USER);
        return user;
    }
}
