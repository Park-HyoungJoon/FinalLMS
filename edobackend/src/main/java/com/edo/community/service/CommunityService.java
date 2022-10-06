package com.edo.community.service;


import com.edo.community.dto.CommunityTestDto;
import com.edo.community.entity.CommunityTest;
import com.edo.community.repository.CommunityTestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommunityService {

    private final CommunityTestRepository communityTestRepository;

    public Long saveContents(CommunityTestDto communityTestDto) throws Exception {

//        게시글 등록
        CommunityTest communityTest = communityTestDto.createContents();
        communityTestRepository.save(communityTest);

        return communityTest.getCommunityId();
    }

    //    게시글 리스트로 나타내기
    public List<CommunityTestDto> getCommunityList(CommunityTest communityTest)  {

        List<CommunityTest> testList = communityTestRepository.findAllByOrderByCommunityId();

//        communitytest -> communityDto형으로 바꿔줄건데 이 때 entity에 Dto로 변환하는 메소드를 작성해줘야한다.
        List<CommunityTestDto> communityTestDtos = testList.stream().map((communityTest1)-> communityTest1.toDtoNoContents()).toList();
        log.info(communityTestDtos.toString());
        return communityTestDtos;
    }

//    게시글 세부 조회


}
