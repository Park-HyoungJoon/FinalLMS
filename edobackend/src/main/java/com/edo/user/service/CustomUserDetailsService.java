//package com.edo.user.service;
//
//import com.edo.user.entity.User;
//import com.edo.user.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//
//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//    @Autowired
//    private UserRepository userRepository;
//
//    //email을 통해 유저가 실제 존재하는지 알아보는 메소드
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findByEmail(username)
//                .map(this::createUserDetails)
//                .orElseThrow(() -> new UsernameNotFoundException(username + "을 DB에서 찾을 수 없습니다."));
//    }
//    private UserDetails createUserDetails(User user){
//        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole().toString());
//
//        return new org.springframework.security.core.userdetails.User(
//                String.valueOf(user.getId()),user.getPassword(), Collections.singleton(grantedAuthority)
//        );
//    }
//}
