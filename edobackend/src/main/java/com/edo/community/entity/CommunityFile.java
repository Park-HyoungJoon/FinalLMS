package com.edo.community.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "community_file")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunityFile {

    @Id
    @Column(name = "community_file_id")
    private int communityFileId;

    @JoinColumn(name = "community_id")
    private int communityId;
}
