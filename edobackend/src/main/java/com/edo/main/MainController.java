package com.edo.main;

import com.edo.community.entity.Community;
import com.edo.community.entity.CommunityTest;
import com.edo.community.repository.CommunityRepository;
import com.edo.community.repository.CommunityTestRepository;
import com.edo.lecture.entity.Lecture;
import com.edo.lecture.repository.LectureRepository;
import com.edo.user.entity.Member;
import com.edo.user.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

import java.util.List;

@CrossOrigin
@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {
	@Autowired
	LectureRepository lectureRepository;

	@Autowired
	CommunityTestRepository communityTestRepository;

	MemberRepository memberRepository;

	@Autowired
	CommunityRepository communityRepository;

	@GetMapping(value="/")
	public String Home(Model model)
		{
			List<Community> communityMainList = communityRepository.findDescCommunity(4);
			log.info("가장 최근 커뮤니티 불러오기 >>>>>>>>>>>>>>>>>>>>>>>>>>> " + communityMainList.get(0).toString() + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"  );
			model.addAttribute("communityMainList",communityMainList);



		List<Lecture> lectureList =  lectureRepository.findAllLectureTopFour(4);
		model.addAttribute("lectureList",lectureList);
		return "index";
	}

	@GetMapping()
	public String memberHeader(Model model){
		List<Member> memberList = memberRepository.findAll();
		model.addAttribute("memberList",memberList);

		return "fragments/header";
	}

	@GetMapping(value = "/test2")
	public String test() {return "lectureContents";}
}
