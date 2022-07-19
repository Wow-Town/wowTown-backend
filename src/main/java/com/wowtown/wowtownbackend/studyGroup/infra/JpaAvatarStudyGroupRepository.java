package com.wowtown.wowtownbackend.studyGroup.infra;

import com.wowtown.wowtownbackend.studyGroup.domain.AvatarStudyGroup;
import com.wowtown.wowtownbackend.studyGroup.domain.AvatarStudyGroupRepository;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface JpaAvatarStudyGroupRepository
        extends JpaRepository<AvatarStudyGroup ,Long>, AvatarStudyGroupRepository {

    @Query("select asg from AvatarStudyGroup as asg where asg.avatar.id =:avatarId " +
            "and asg.studyGroup.id =:studyGroupId")
    Optional<AvatarStudyGroup> findByAvatarIdAndStudyGroupId(
            @Param("avatarId") Long avatarId, @Param("studyGroupId") Long studyGroupId);
    @Query("select asg from AvatarStudyGroup as asg where asg.avatar.id =:avatarId " +
            "and asg.studyGroup.id =:studyGroupId and asg.isHost = 1 ")
    Optional<AvatarStudyGroup> findByAvatarIdAndStudyGroupIdHost
            (@Param("avatarId") Long avatarId, @Param("studyGroupId") Long studyGroupId);

}
