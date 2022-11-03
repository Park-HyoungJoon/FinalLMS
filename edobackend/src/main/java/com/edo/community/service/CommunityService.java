package com.edo.community.service;


import com.edo.community.dto.CommunityDto;
import com.edo.community.entity.Community;
import com.edo.community.entity.CommunityTest;
import com.edo.community.repository.CommunityTestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommunityService {

    private final CommunityTestRepository communityTestRepository;

    public Long saveContents(CommunityDto communityDto) throws Exception {

//        게시글 등록
        CommunityTest communityTest = communityDto.createContents();
        communityTestRepository.save(communityTest);

        return communityTest.getCommunityId();
    }

    //    게시글 리스트로 나타내기
    public List<CommunityDto> getCommunityList(CommunityTest communityTest)  {

        List<CommunityTest> testList = communityTestRepository.findAllByOrderByCommunityId();

//        communitytest -> communityDto형으로 바꿔줄건데 이 때 entity에 Dto로 변환하는 메소드를 작성해줘야한다.
        List<CommunityDto> communityDtos = testList.stream().map((communityTest1)-> communityTest1.toDtoNoContents()).toList();
        log.info(communityDtos.toString());
        return communityDtos;
    }

//    게시글 세부 조회

    //	Mapper로 넘기기
//	Dto->community니까 생성자로 Dto 넘겨준다
    public Community createRealContents(CommunityDto communityDto){
        ModelMapper modelMapper  = new ModelMapper();
        Community community = modelMapper.map(communityDto, Community.class);
        return community;
    }



}
