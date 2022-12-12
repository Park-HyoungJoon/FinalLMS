package com.edo.user.service;

import com.edo.user.entity.MemberImage;
import com.edo.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberImgService {

    @Value(value = "${uploadPathMinju}")
    private String memberImgLocation;

    private final MemberRepository memberRepository;

    private final FileService fileService;

//    이미지 저장
    public void savdMemberImg(MemberImage memberImage, MultipartFile memberImgFile ) throws IOException {
        String oriImgName = memberImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(memberImgLocation, oriImgName, memberImgFile.getBytes());
            imgUrl = "/images/item/" + imgName;
        }
    }
}
