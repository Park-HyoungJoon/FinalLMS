package com.edo.main;

import com.edo.community.entity.CommunityTest;
import com.edo.community.repository.CommunityTestRepository;
import com.edo.lecture.entity.Lecture;
import com.edo.lecture.repository.LectureRepository;
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
public class MainController {
	@Autowired
	LectureRepository lectureRepository;

	@Autowired
	CommunityTestRepository communityTestRepository;

	@GetMapping(value="/")
	public String Home(Model model)
	{
		List<CommunityTest> communityMainList = communityTestRepository.findDescCommunity(4);
		model.addAttribute("communityMainList",communityMainList);

		List<Lecture> lectureList =  lectureRepository.findAllLectureTopFour(4);
		model.addAttribute("lectureList",lectureList);
		return "index";
	}

	@GetMapping(value = "/test2")
	public String test() {return "lectureContents";}
}
