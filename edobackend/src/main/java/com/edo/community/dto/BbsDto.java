package com.edo.community.dto;

import com.edo.user.entity.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "member")
public class BbsDto {
    private Long id;

    private Member member;

    private String title;

    private String content;

    private int hit;
}
