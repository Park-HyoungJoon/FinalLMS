package com.edo.lecture.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@CrossOrigin
@Controller
@RequiredArgsConstructor
public class lectureController {

    @GetMapping(value="/lecture")
    public String Home(){
        return "lecture/lecture";
    }

    @GetMapping(value="/lecture/add")
    public String lectureAdd2() {
        return "lecture/lectureAdd";
    }
    @PostMapping(value="/lecture/add")
    public String lectureAdd() {
        return "lecture/lectureAdd";
    }

    @GetMapping(value="/lecture/contents")
    public String lectureContents() {
        return "/lecture/lectureContents";
    }

}
