package com.edo.user.service;

import javax.persistence.EntityNotFoundException;

import com.edo.community.dto.CommunityTestDto;
import com.edo.community.entity.CommunityTest;
import com.edo.user.dto.MemberDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edo.user.entity.Member;
import com.edo.user.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
@Slf4j
public class MemberService implements UserDetailsService
{

	// @Autowired
	private final MemberRepository memberRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

	// 회원가입 시 멤버 저장
	public Long saveMember(Member member)
	{

		log.info(member.toString());

//        중복된 회원인지 먼저 확인

		validateDuplicateMemberEmail(member.getMemberEmail());

//        중복되지 않았다면 회원가입
		Member saveMember = memberRepository.save(member);
		log.info(saveMember.toString());
		return saveMember.getMemberId();
	}

	// 가입된 회원에 한하여 예외 발생
	public void validateDuplicateMemberEmail(String memberEmail)
	{
//        이메일 중복 확인
//		Member findMember = memberRepository.findByMemberEmail(memberEmail).get();
		Optional<Member> findMember = memberRepository.findByMemberEmail(memberEmail);
		if (findMember.isPresent())

		{
			throw new IllegalStateException("이미 가입된 회원입니다.");
		}

	}

	// 닉네임 중복 확인
	public void validateDuplicateNickname(String memberNickname)
	{
		Member findUserNickname = memberRepository.findByMemberNickname(memberNickname);
		if (findUserNickname != null)
		{
			throw new IllegalStateException("중복된 닉네임입니다. 다른 닉네임으로 설정해주세요");
		}
	}

//    로그인

	@Override
	public UserDetails loadUserByUsername(String memberEmail) throws UsernameNotFoundException
	{
		log.info("===========>" + memberEmail);
		Member member = memberRepository.findByMemberEmail(memberEmail).orElseThrow(()->new EntityNotFoundException("오류"));

		log.info("===========>" + member.getMemberEmail() + ", " + member.getMemberRole().toString());
		return User.builder() // User 객체 생성하기
				.username(member.getMemberEmail()).password(member.getMemberPassword()).roles(member.getMemberRole().toString())
				.build();
	}

//	마이페이지 member이름 가져오기
//    게시글 리스트로 나타내기
	public List<MemberDto> getMemberList(Member member){
		List<Member> memberList = memberRepository.findAllByOrderByMemberId();

//		entity에 작성한 코드를 list로 받아온다.
		List<MemberDto> memberDtos  = memberList.stream().map((member1 -> member1.toMemberDto())).toList();
		log.info(memberDtos.toString());
		return memberDtos;
	}

}
