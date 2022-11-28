package com.edo.lecture.entity;

import com.edo.user.entity.Member;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
//강좌 구독 및 좋아요
public class LectureMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_member_id")
    private Long id;


    //강좌 매핑(다대일 / 강좌Id가 테이블에 등록됨)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="lecture_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Lecture lecture;

    //멤버 매핑(다대일 / 멤버Id가 테이블에 등록됨)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    private int subscribe;

    private int heart;

    private int listen;

}
