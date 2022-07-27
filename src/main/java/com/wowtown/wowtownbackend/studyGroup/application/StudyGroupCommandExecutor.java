package com.wowtown.wowtownbackend.studyGroup.application;

import com.wowtown.wowtownbackend.avatar.application.AvatarCommandExecutor;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
import com.wowtown.wowtownbackend.chatroom.application.MultiChatRoomCommandExecutor;
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
  private final AvatarCommandExecutor avatarQueryProcessor;
  private final StudyGroupRepository studyGroupRepository;
  private final StudyGroupMapper studyGroupMapper;
  private final MultiChatRoomCommandExecutor multiChatRoomCommandExecutor;

  @Transactional
  public long createStudyGroup(CreateOrUpdateStudyGroupDto dto, Avatar avatar) { // 공고등록
    // 같은 공고 제목은 체크하지 않습니다. 제목은 unique한 것이 아니기 때문입니다.
    StudyGroup studyGroup =
        studyGroupMapper.toStudyGroup(
            dto.getSubject(),
            dto.getPersonnel(),
            dto.getDescription(),
            dto.getInterests(),
            dto.getStatus());


    // 공고를 생성한 아바타를 host로 설정하여 아바타-공고를 추가한다.
    studyGroup.avatarJoinStudyGroup(avatar, StudyGroupRole.HOST);
    multiChatRoomCommandExecutor.createMultiChatRoom(studyGroup.getId(),dto.getSubject(),dto.getPersonnel());
    studyGroupRepository.save(studyGroup);

    return studyGroup.getId();
  }

  @Transactional
  public boolean updateStudyGroup(
      long studyGroupId, CreateOrUpdateStudyGroupDto dto, Avatar avatar) {
    StudyGroup findStudyGroup =
        studyGroupRepository
            .findById(studyGroupId)
            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 스터디 그룹입니다"));

    if (findStudyGroup.checkAvatarStudyGroupRoleIsHost(avatar)) {
      findStudyGroup.updateStudyGroup(
          studyGroupMapper.toStudyGroup(
              dto.getSubject(),
              dto.getPersonnel(),
              dto.getDescription(),
              dto.getInterests(),
              dto.getStatus()));
      multiChatRoomCommandExecutor.updateMultiChatRoomWithStudyGroupUpdate(findStudyGroup.getId(),dto.getSubject(),dto.getPersonnel());
      return true;
    }
    throw new InstanceNotFoundException("참여중인 스터디 그룹이 아니거나 방장이 아닙니다.");
  }

  @Transactional
  public boolean deleteStudyGroup(long studyGroupId, Avatar avatar) {
    StudyGroup findStudyGroup =
        studyGroupRepository
            .findById(studyGroupId)
            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 스터디 그룹입니다"));

    if (findStudyGroup.checkAvatarStudyGroupRoleIsHost(avatar)) {
      studyGroupRepository.delete(findStudyGroup);
      return true;
    }

    throw new InstanceNotFoundException("참여중인 스터디 그룹이 아니거나 방장이 아닙니다.");
  }
}
