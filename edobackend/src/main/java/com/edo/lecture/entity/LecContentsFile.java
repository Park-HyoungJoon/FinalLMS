package com.edo.lecture.entity;

import com.edo.config.BaseEntity;
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
//강의자료 파일
/**
 * 강의자료 파일
 *
 */
public class LecContentsFile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long LCFId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LCId")
    private Lec_Contents lec_contents;
}
