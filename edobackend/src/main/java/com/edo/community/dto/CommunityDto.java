package com.edo.community.dto;

import com.edo.community.constant.Section;
import com.edo.community.entity.Community;
import com.edo.community.entity.CommunityTest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@ToString
public class CommunityDto {

   private Long communityId;

   private Long memberId;
   
   @NotBlank(message = "제목을 입력해주세요")
    private String communityTitle;

   @NotBlank(message = "내용을 입력해주세요")
   private String communityContents;

   private int communityHits;

   private Section communitySection;

   private String communityNickname;

   private static ModelMapper modelMapper = new ModelMapper();

//   test용
    public CommunityTest createContents(){
        return  modelMapper.map(this, CommunityTest.class);
    }

//    이게 진짜
    public Community createRealContents(){return modelMapper.map(this, Community.class);}

    public static CommunityDto of(Community community){
        return modelMapper.map(community, CommunityDto.class);
    }
//   public static CommunityDto of (CommunityTest communityTest){
//       return modelMapper.map(communityTest, CommunityDto.class);
//   }





}

