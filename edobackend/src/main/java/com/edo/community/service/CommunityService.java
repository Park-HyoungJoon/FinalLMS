package com.edo.community.service;


import com.edo.community.dto.CommunityTestDto;
import com.edo.community.entity.Community;
import com.edo.community.entity.CommunityTest;
import com.edo.community.repository.CommunityRepository;
import com.edo.community.repository.CommunityTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityTestRepository communityTestRepository;

    public Long saveContents(CommunityTestDto communityTestDto) throws Exception{

//        게시글 등록
        CommunityTest communityTest = communityTestDto.createContents();
        communityTestRepository.save(communityTest);

        return communityTest.getCommunityId();
    }

}
