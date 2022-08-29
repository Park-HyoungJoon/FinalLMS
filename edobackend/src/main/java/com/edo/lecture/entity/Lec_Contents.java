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
//강의자료
public class Lec_Contents {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Lec_contents_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LDId")
    private Lec_Divide lec_divide;

    //파일타입
    private String LCType;

    //강의자료 제목
    private String LCTitle;

    //파일 타입이 URL인 경우
    private String LCURL;

    //파일 타입이 youtube_url인 경우
    private String LCYoutube_URL;

    //파일 타입이 HHTML인 경우
    private String LCHTML;
}
