package com.edo.user.entity;

import com.edo.util.item.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
//강사
public class Teacher extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Long id;

    //식별관계 User 매핑
    @MapsId
    @OneToOne
    @JoinColumn(name="users_id")
    private Users users;

    //대표이미지
    @Column(columnDefinition = "LONGTEXT")
    private String teacherImgUrl;

    //강사소개
    @Column(columnDefinition = "TEXT")
    private String teacherInfo;
}
