package com.edo.lecture.entity;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
//강의자료
public class LectureContents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_contents_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_devide_id")
    private LectureDivide lectureDivide;

    //파일타입
    @Column(length = 1)
    private String lectureContentsType;

    //강의자료 제목
    @Column(length = 255)
    private String lectureContentsTitle;

    //파일 타입이 URL인 경우
    @Column(columnDefinition = "TEXT")
    private String lectureContentsUrl;

    //파일 타입이 youtube_url인 경우
    @Column(columnDefinition = "TEXT")
    private String lctureContentsYoutubeUrl;

    //파일 타입이 HHTML인 경우
    @Column(columnDefinition = "TEXT")
    private String lectureContentsHtml;
}
