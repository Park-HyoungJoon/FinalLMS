package com.edo.user.entity;

import javax.persistence.*;

import com.edo.user.constant.Role;
import com.edo.util.item.BaseTimeEntity;
import lombok.*;

import com.edo.user.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
//회원
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long usersId;

    @Column(name = "users_email", length = 255)
	private String usersEmail;

    //비밀번호
    @Column(name = "users_password",length = 255)
    private String usersPassword;

    //닉네임(Unique)`
    @Column(name = "users_nickname", unique = true , length = 10)
	private String usersNickname;

    //이름
    @Column(name = "users_name",length = 60)
    private String usersName;

    //핸드폰
    @Column(name = "users_phone",length = 15)
    private Long usersPhone;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "users_role")
//    private Role usersRole;

    //대표이미지
    //image url 주소를 넣을예정
//    @Column(columnDefinition = "LONGTEXT",name = "users_image")
//	private String usersImageUrl;

//    시큐리티 설정을 지웠기에 일단 주석처리(다시 사용할 예정)
//    유저를 회원가입 할 때 비밀번호를 암호화하는 메서드

//    public static Users createUser(UserDto userDto, PasswordEncoder passwordEncoder){
//        Users users = new Users();
//        users.setUsersName(userDto.getUsersName());
//        users.setUsersNickname(userDto.getUsersNickname());
//        users.setUsersPhone(userDto.getUsersPhone());
//        String password = passwordEncoder.encode(userDto.getUsersPassword());
//        users.setUsersPassword(password);
//        return users;
//    }


    public void createUsers(String usersEmail, String usersPassword, String usersNickname, String usersName, Long usersPhone,
                 Role usersRole){
        this.usersEmail = usersEmail;
        this.usersPassword = usersPassword;
        this.usersNickname = usersNickname;
        this.usersName = usersName;
        this.usersPhone = usersPhone;

    }
}
