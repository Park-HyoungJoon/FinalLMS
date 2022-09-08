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
        return "lectureAdd";
    }
    @PostMapping(value="/lecture/add")
    public String lectureAdd() {
        return "lectureAdd";
    }
}
