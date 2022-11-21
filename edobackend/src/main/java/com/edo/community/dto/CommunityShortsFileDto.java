package com.edo.community.dto;

import com.edo.community.entity.CommunityShorts;
import com.edo.lecture.entity.LectureContents;
import com.edo.lecture.entity.LectureContentsFile;
import com.edo.user.entity.Member;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommunityShortsFileDto {


    private Long id;
    private Member member;




    public CommunityShorts toCommunityShorts(MultipartFile file,Member member,String fileName){
        if(file==null){
            return null;
        }
        //파일 이름 file.getOriginalFilename();
        //파일 컨텐츠 타입file.getContentType();
        //파일 위치 file.getResource()
        CommunityShorts communityShorts = new CommunityShorts();
        communityShorts.setFileName(fileName);
        communityShorts.setFileLoc(String.valueOf(file.getResource()));
        communityShorts.setFileSize(file.getSize());
        communityShorts.setFileType(file.getContentType());
        communityShorts.setMember(member);
        return communityShorts;
    }
}
