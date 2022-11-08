package com.edo.community.dto;

import com.edo.community.constant.Section;
import com.edo.community.entity.Community;
import com.edo.community.entity.CommunityTest;
import com.edo.user.entity.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@ToString(exclude = "member")
public class CommunityDto {

   private Long id;

   private Long memberId;
   
   @NotBlank(message = "제목을 입력해주세요")
    private String title;

   @NotBlank(message = "내용을 입력해주세요")
   private String content;

   private int hit;

   private String category;


   private static ModelMapper modelMapper = new ModelMapper();

//   test용
    public CommunityTest createContents(){
        return  modelMapper.map(this, CommunityTest.class);
    }

//    이게 진짜
    public Community createRealContents(){return modelMapper.map(this, Community.class);}



}

