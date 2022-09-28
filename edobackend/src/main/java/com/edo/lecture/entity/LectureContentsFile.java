package com.edo.lecture.entity;

import com.edo.util.item.BaseFileEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
//강의자료 파일
/**
 * 강의자료 파일
 *
 */
public class LectureContentsFile extends BaseFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_contents_file_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_contents_id")
    private LectureContents lectureContents;

}

