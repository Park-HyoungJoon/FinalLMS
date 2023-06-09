package com.edo.user.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edo.user.entity.Member;
import com.edo.user.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
@Slf4j
public class MemberService implements UserDetailsService {

    // @Autowired
    private final MemberRepository memberRepository;


//    @Autowired
//    private final PasswordEncoder passwordEncoder;

    // 회원가입 시 멤버 저장
    public Long saveMember(Member member) {

        log.info(member.toString());

//        중복된 회원인지 먼저 확인

        validateDuplicateMemberEmail(member.getMemberEmail());

//        중복되지 않았다면 회원가입
        Member saveMember = memberRepository.save(member);
        log.info(saveMember.toString());
        return saveMember.getMemberId();
    }

    // 가입된 회원에 한하여 예외 발생
    public void validateDuplicateMemberEmail(String memberEmail) {
//        이메일 중복 확인
//		Member findMember = memberRepository.findByMemberEmail(memberEmail).get();
        Optional<Member> findMember = memberRepository.findByMemberEmail(memberEmail);
        if (findMember.isPresent()) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }

    }


//    이메일 확인하기
    public void emailCheck(String memberEmail){
        Optional<Member> findMember = memberRepository.findByMemberEmail(memberEmail);
        if (findMember.isEmpty()) {
            throw new IllegalStateException("회원 정보가 존재하지 않습니다!");
        }
    }

    // 닉네임 중복 확인
    public void validateDuplicateNickname(String memberNickname) {
        Member findUserNickname = memberRepository.findByMemberNickname(memberNickname);
        if (findUserNickname != null) {
            throw new IllegalStateException("중복된 닉네임입니다. 다른 닉네임으로 설정해주세요");
        }
    }

//    로그인

    @Override
    public UserDetails loadUserByUsername(String memberEmail) throws UsernameNotFoundException {
        log.info("===========>" + memberEmail);
        Member member = memberRepository.findByMemberEmail(memberEmail).orElseThrow(() -> new EntityNotFoundException("오류"));

        log.info("===========>" + member.getMemberEmail() + ", " + member.getMemberRole().toString());
        return User.builder() // User 객체 생성하기
                .username(member.getMemberEmail()).password(member.getMemberPassword()).roles(member.getMemberRole().toString())
                .build();
    }

//    멤버 수정하기
//    public void MemberUpdate(Optional<Member> member){
//        member = memberRepository.findByMemberId(member.get().getMemberId());
//
//
//        if (member == null){
//            throw new IllegalStateException("없는 회원입니다.");
//        } else {
//
//            String planePw = member.get().getMemberPassword();
////            String memberpassword =passwordEncoder.encode(planePw);
//
//
//        }
//    }

    //	커뮤니티로 닉네임 보내기
    public String communityNickname(String memberEmail) {
        Optional<Member> member = memberRepository.findByMemberEmail(memberEmail);
        return member.get().getMemberNickname();
    }

    //	커뮤니티로 멤버 보내기
    public Member communityMember(String memberEmail) {
        Optional<Member> member = memberRepository.findByMemberEmail(memberEmail);
        return member.get();

    }public Member mypageMember(Long memberId) {
        Optional<Member> member = memberRepository.findByMemberId(memberId);
        return member.get();
    }


}
