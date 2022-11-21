package com.edo.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin
@Controller
@RequiredArgsConstructor
@Slf4j
public class ManagerController {
    @GetMapping("/manager/managerMain")
    public String managerMain(){
        return "manager/managerMain";
    }
}
