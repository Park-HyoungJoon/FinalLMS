package com.edo.user.entity;

import com.edo.lecture.entity.Lecture;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
//수강생
public class User_Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ULId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LId")
    private Lecture lecture;

    //좋아요 체크,미체크
    private String like_YN;

}
