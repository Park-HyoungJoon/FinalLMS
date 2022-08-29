//package com.edo.user.dto;
//
//import com.edo.constant.Role;
//import com.edo.user.entity.User;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
////Request를 받을 때 쓰이는 dto. UsernamePasswordAuthenticationToken을 반환하여
////아이디와 비밀번호가 일치하는지 검증하는 로직을 넣을 수 있게 된다.
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class UserRequestDto {
//    private String email;
//    private String password;
//    private String name;
//
//    private String address;
//
//    public User toUser(PasswordEncoder passwordEncoder){
//        return User.builder()
//                .email(email)
//                .password(passwordEncoder.encode(password))
//                .name(name)
//                .address(address)
//                .role(Role.ROLE_USER)
//                .build();
//    }
//
//    public UsernamePasswordAuthenticationToken toAuthentication(){
//        return new UsernamePasswordAuthenticationToken(email,password);
//    }
//}
