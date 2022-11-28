package com.edo.lecture.dto;


import com.edo.lecture.entity.Lecture;
import com.edo.lecture.entity.LectureMember;
import com.edo.user.entity.Member;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LectureMemberDto {
    private Long id;


    private Lecture lecture;

    private Member member;

    private int subscribe;

    private int heart;

    private int listen;

/*
    public LectureMemberDTO(LectureMember entity) {
        this.id = entity.getId();
        this.lecture = entity.getLecture();
        this.member = entity.getMember();
        this.subscribe = entity.getSubscribe();
        this.like = entity.getLike();
    }*/

    public LectureMember toEntity(LectureMemberDto dto) {
        return LectureMember.builder()
                .id(dto.getId())
                .heart(dto.getHeart())
                .member(dto.getMember())
                .subscribe(dto.getSubscribe())
                .lecture(dto.getLecture())
                .listen(dto.getListen())
                .build();
    }
}
