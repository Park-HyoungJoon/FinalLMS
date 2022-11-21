package com.edo.user.entity;

import javax.persistence.*;

import com.edo.community.entity.Community;
import com.edo.user.constant.Role;
import com.edo.util.item.BaseTimeEntity;
import lombok.*;

import com.edo.user.dto.MemberDto;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "member")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "communityList")
//회원

public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

//    member id를 외래키로 잡게 하기 위함
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Community> communityList = new ArrayList<>();

    @Column( length = 255)
	private String memberEmail;

    //비밀번호
    @Column(length = 255 )
    private String memberPassword;

    //닉네임(Unique)`
    @Column(unique = true , length = 10)
	private String memberNickname;


    //이름
    @Column(length = 60)
    private String memberName;

    //핸드폰
    @Column(length = 15)
    private String memberPhone;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role")
    private Role memberRole;

    //대표이미지
    //image url 주소를 넣을예정
//    @Column(columnDefinition = "LONGTEXT",name = "users_image")
//	private String usersImageUrl;

//    시큐리티 설정을 지웠기에 일단 주석처리(다시 사용할 예정)
//    유저를 회원가입 할 때 비밀번호를 암호화하는 메서드

    public static Member createUser(MemberDto memberDto, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setMemberEmail(memberDto.getMemberEmail());
        member.setMemberName(memberDto.getMemberName());
        member.setMemberNickname(memberDto.getMemberNickname());
        member.setMemberPhone(memberDto.getMemberPhone());
        String password = passwordEncoder.encode(memberDto.getMemberPassword());
        member.setMemberPassword(password);
        member.setMemberRole(Role.USER); // 기본값은 학생
        return member;
    }



//    mypage에서 list로 받아오기 위해 memberDto로 변환
    public MemberDto toMemberDto(){
        MemberDto memberDto = new MemberDto();
        memberDto.setMemberName(this.memberName);
        memberDto.setMemberNickname(this.memberNickname);
        memberDto.setMemberPhone(this.memberPhone);
        return memberDto;
    }
}
