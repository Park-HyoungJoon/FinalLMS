package com.edo.community.entity;

import com.edo.util.item.BaseFileEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "community_file")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunityFile extends BaseFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_file_id")
    private Long communityFileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community communityId;
}
