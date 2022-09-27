package com.edo.community.dto;

import com.edo.community.entity.CommunityTest;
import com.edo.user.entity.Users;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@ToString
public class CommunityTestDto {

   private Long communityId;
   
   @NotBlank(message = "제목을 입력해주세요")
    private String communityTitle;

   @NotBlank(message = "내용을 입력해주세요")
   private String communityContents;

   private static ModelMapper modelMapper = new ModelMapper();

    public CommunityTest createContents(){
        return  modelMapper.map(this, CommunityTest.class);
    }

   public static CommunityTestDto of (CommunityTest communityTest){
       return modelMapper.map(communityTest, CommunityTestDto.class);
   }





}

