package com.edo.user.service;

import com.edo.config.TokenProvider;
import com.edo.config.dto.TokenDto;
import com.edo.user.dto.UserRequestDto;
import com.edo.user.dto.UserResponseDto;
import com.edo.user.entity.User;
import com.edo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    //평범하게 회원가입하는 메서드
    public UserResponseDto signup(UserRequestDto requestDto){
        if(userRepository.existsByEmail(requestDto.getEmail())){
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }
        User user = requestDto.toUser(passwordEncoder);
        return UserResponseDto.of(userRepository.save(user));
    }

    /*
    * 1. UserRequestDto에 있는 메서드 toAuthentication를 통해 생긴
    * UsernamePasswordAuthenticationToken 타입의 데이터를 갖는다.
    *
    * 2.주입받은 Builder를 통해 AuthenticationManager를 구현한 ProviderManager를 생성한다.
    *
    * 3.이후 ProviderManager는 데이터를
    * AbstractUserDetailsAuthenticationProvider의 자식 클래스인
    * DaoAuthenticationProvider를 주입받아서 호출한다.
    *
    * 4.DaoAuthenticationProvider 내부에 있는 authenticate에서 retrieveUser를 통해
    * DB에서의 User의 비밀번호가 실제 비밀번호가 맞는지 비교
    *
    * 5.retrieveUser에서는 DB에서의 User를 꺼내기 위해,
    * CustomUserDetailService에 있는 loadUserByUsername을 가져와 사용
    *
    * */
    public TokenDto login(UserRequestDto requestDto){
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();

        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

        return tokenProvider.generateTokenDto(authentication);
    }

}
