package com.edo.lecture.repository;

import com.edo.lecture.entity.LectureMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LectureSubscribeRepository extends JpaRepository<LectureMember,Long> {
    @Query(value = "select l.lecture_member_id from lecture_member l where l.member_id=?1 and l.lecture_id=?2",nativeQuery = true)
    Long searchIdByLectureAndMember(Long id,Long lectureId);

    @Query(value = "select l.heart from lecture_member l where l.member_id=?1 and l.lecture_id=?2",nativeQuery = true)
    int searchHeartByLectureAndMember(Long id,Long lectureId);

    @Query(value="select lm.lecture_id from lecture_member lm where lm.member_id =?1 and lm.heart =1 order by lecture_id desc limit 5",nativeQuery = true)
    List<Long> searchLectureIdByMemberAndHeart(Long id);
}
