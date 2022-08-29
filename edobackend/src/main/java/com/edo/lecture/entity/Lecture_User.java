package com.edo.lecture.entity;

import com.edo.user.entity.User;
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
//강좌와 수강생
public class Lecture_User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long L_UId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Uemail")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "L_Id")
    private Lecture lecture;

    //좋아요 버튼 눌렀는지 아닌지
    private char like_yn = 'N';
}
