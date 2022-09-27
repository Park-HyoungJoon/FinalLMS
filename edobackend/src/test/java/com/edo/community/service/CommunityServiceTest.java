package com.edo.community.service;

import com.edo.community.dto.CommunityTestDto;
import com.edo.community.repository.CommunityTestRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class CommunityServiceTest {
    @Autowired
    CommunityService communityService;
    
    @Autowired
    CommunityTestRepository communityTestRepository;
    
    @Test
    @DisplayName("게시글 등록 테스트")
    void createContents() throws Exception{
        CommunityTestDto communityTestDto = new CommunityTestDto();
        communityTestDto.setCommunityTitle("테스트 제목");
        communityTestDto.setCommunityContents("테스트 본문");
    }

}