package com.edo.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@Controller
@RequiredArgsConstructor
public class MainController {

	@GetMapping(value="/")
	public String Home(){
		return "index";
	}
}
