package com.edo.lecture.entity;

import com.edo.util.item.BaseFileEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
//학습자료_파일
public class MasterPlanFile extends BaseFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "master_plan_file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_plan_id ")
    private MasterPlan masterPlan;
}
