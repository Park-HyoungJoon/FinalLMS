package com.edo.main;

import com.edo.community.entity.CommunityTest;
import com.edo.community.repository.CommunityTestRepository;
import com.edo.lecture.entity.Lecture;
import com.edo.lecture.repository.LectureRepository;
import com.edo.lecture.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@CrossOrigin
@Controller
@RequiredArgsConstructor
public class MainController {
	@Autowired
	LectureRepository lectureRepository;

	@Autowired
	CommunityTestRepository communityTestRepository;

	@Autowired
	LectureService lectureService;

	@GetMapping(value="/")
	public String Home(Model model,
					   @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
					   @RequestParam(value = "size", required = false, defaultValue = "4") int size)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String path = auth.getName();
		List<Lecture> lectureList = lectureRepository.findAll();
		model.addAttribute("posts", lectureService.getPage(pageNumber, size));
		model.addAttribute("list", lectureList);

		List<CommunityTest> communityMainList = communityTestRepository.findDescCommunity(4);
		model.addAttribute("communityMainList",communityMainList);
		return "index";
	}

	@GetMapping(value = "/test2")
	public String test() {return "lectureContents";}
}
