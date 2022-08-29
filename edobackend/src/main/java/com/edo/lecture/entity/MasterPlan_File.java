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
//학습자료_파일
public class MasterPlan_File extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long MPFId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MPId")
    private MasterPlan masterPlan;
}
