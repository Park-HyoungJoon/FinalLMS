package com.edo.lecture.entity;

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
//차시
public class Lec_Divide {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long LDId;

    //강좌 매핑(다대일 / 강좌Id가 테이블에 등록됨)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="LId")
    private Lecture lecture;
    
    //차시제목
    private String title;

    //파일 url
    private String fileURL;
    //차시순번
    private int LDseq;

}
