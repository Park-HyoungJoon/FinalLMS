package com.edo.community.controller;

import com.edo.community.entity.Bbs;
import com.edo.community.service.BbsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/bbs")
public class BbsController {

    private final BbsService bbsService;

    @GetMapping(value = "/list")
    public String bbsList(Model model){

       List<Bbs> bbsList = bbsService.getList();
       model.addAttribute("bbsList",bbsList);
       log.info("리스트 갯수는 " + bbsList.size());
        return "bbs/list";
    }


}
