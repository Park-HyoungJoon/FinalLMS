package com.edo.community.entity;

import com.edo.user.entity.Member;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

public class CommunityShortsComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_shorts_comment_id")
    private Long id;

    private String shortsComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="community_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Community community;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

}
