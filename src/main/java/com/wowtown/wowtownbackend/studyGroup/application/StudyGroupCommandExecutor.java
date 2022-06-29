package com.wowtown.wowtownbackend.studyGroup.application;

import com.wowtown.wowtownbackend.avatar.application.AvatarQueryProcessor;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.studyGroup.application.common.StudyGroupMapper;
import com.wowtown.wowtownbackend.studyGroup.application.dto.request.CreateOrUpdateStudyGroupDto;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroup;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroupRepository;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroupRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudyGroupCommandExecutor {
  private final AvatarQueryProcessor avatarQueryProcessor;
  private final StudyGroupRepository studyGroupRepository;
  private final StudyGroupMapper studyGroupMapper;

  @Transactional
  public long createStudyGroup(CreateOrUpdateStudyGroupDto dto) { // 공고등록
    // 같은 공고 제목은 체크하지 않습니다. 제목은 unique한 것이 아니기 때문입니다.
    Avatar findAvatar = avatarQueryProcessor.getAvatar(dto.getAvatarId());

    StudyGroup studyGroup =
        studyGroupMapper.toStudyGroup(
            dto.getSubject(), dto.getPersonnel(), dto.getDescription(), dto.getStatus());

    // 공고를 생성한 아바타를 host로 설정하여 아바타-공고를 추가한다.
    studyGroup.addAvatarStudyGroup(findAvatar, StudyGroupRole.HOST);

    studyGroupRepository.save(studyGroup);

    return studyGroup.getId();
  }

  // 현재 완전히 구현하지 못 함(방장만이 삭제,수정 가능)
  public boolean updateStudyGroup(long studyGroupId, CreateOrUpdateStudyGroupDto dto) {
    StudyGroup findStudyGroup =
        studyGroupRepository
            .findById(studyGroupId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디 그룹입니다"));

    Avatar avatar = avatarQueryProcessor.getAvatar(dto.getAvatarId());

    if (findStudyGroup.checkAvatarStudyGroupRoleIsHost(avatar)) {
      findStudyGroup.updateStudyGroup(
          studyGroupMapper.toStudyGroup(
              dto.getSubject(), dto.getPersonnel(), dto.getDescription(), dto.getStatus()));
      return true;
    }
    throw new IllegalArgumentException("참여중인 스터디 그룹이 아니거나 방장이 아닙니다.");
  }

  @Transactional
  public boolean deleteStudyGroup(long studyGroupId) {
    StudyGroup findStudyGroup =
        studyGroupRepository
            .findById(studyGroupId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디 그룹입니다"));

    studyGroupRepository.delete(findStudyGroup);
    return true;
  }
}
