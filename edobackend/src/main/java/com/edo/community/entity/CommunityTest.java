package com.edo.community.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "community_Test")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CommunityTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id")
    private Long communityId;

    //    제목
    @Column(name = "community_Title")
    private String communityTitle;

    //    내용
    @Column(name = "community_content")
    private String communityContent;


//    id, 제목, 내용을 받아서 게시글을 등록하는 메소드
    public void updateContents(Long communityId, String communityTitle, String communityContent){
        this.communityId = communityId;
        this.communityTitle = communityTitle;
        this.communityContent = communityContent;
    }

}
