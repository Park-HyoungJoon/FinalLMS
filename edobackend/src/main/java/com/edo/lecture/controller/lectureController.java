package com.edo.lecture.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin
@Controller
@RequiredArgsConstructor
public class lectureController {

    @GetMapping(value="/lecture")
    public String Home(){
        return "lecture/lecture";
    }
}
