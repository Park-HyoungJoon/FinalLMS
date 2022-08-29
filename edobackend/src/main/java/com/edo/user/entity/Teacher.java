package com.edo.user.entity;

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
//강사
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long TId;
    
    //식별관계 User 매핑
    @MapsId
    @OneToOne
    @JoinColumn(name="UId")
    private User user;

    //대표이미지
    private String imgUrl;

    //강사소개
    private String TeacherInfo;
}
