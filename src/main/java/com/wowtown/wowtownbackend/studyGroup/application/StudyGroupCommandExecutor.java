package com.wowtown.wowtownbackend.studyGroup.application;

import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
import com.wowtown.wowtownbackend.studyGroup.application.common.PasswordGenerator;
import com.wowtown.wowtownbackend.studyGroup.application.common.StudyGroupMapper;
import com.wowtown.wowtownbackend.studyGroup.application.dto.request.CreateOrUpdateStudyGroupDto;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroup;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudyGroupCommandExecutor {
  private final StudyGroupRepository studyGroupRepository;
  private final StudyGroupMapper studyGroupMapper;
  private final PasswordGenerator passwordGenerator;

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

    // 공고 주인 설정.
    studyGroup.addOwner(avatar);
    // 공고 비밀번호 설정, 오픈채팅방에 입장하려면 방장으로부터 비밀번호를 받고 일치해야 입장 가능하다.
    studyGroup.setDefaultPW(passwordGenerator.generateRandomPassword());

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

    if (findStudyGroup.isSameOwner(avatar)) {
      findStudyGroup.updateStudyGroup(
          studyGroupMapper.toStudyGroup(
              dto.getSubject(),
              dto.getPersonnel(),
              dto.getDescription(),
              dto.getInterests(),
              dto.getStatus()));
      return true;
    }
    throw new InstanceNotFoundException("게시물 소유자가 아닙니다.");
  }

  @Transactional
  public boolean deleteStudyGroup(long studyGroupId, Avatar avatar) {
    StudyGroup findStudyGroup =
        studyGroupRepository
            .findById(studyGroupId)
            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 스터디 그룹입니다"));

    if (findStudyGroup.isSameOwner(avatar)) {
      studyGroupRepository.delete(findStudyGroup);
      return true;
    }
    throw new InstanceNotFoundException("게시물 소유자가 아닙니다.");
  }

  public void addChatRoomInfo(StudyGroup studyGroup, UUID chatRoomUUID) {
    studyGroup.addChatRoomInfo(chatRoomUUID);
  }

  // todo 공고 스크랩 기능 나중에 추가

  //  @Transactional
  //  public void scrapStudyGroup(long studyGroupId, Avatar avatar) {
  //    StudyGroup findStudyGroup =
  //        studyGroupRepository
  //            .findById(studyGroupId)
  //            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 스터디 그룹입니다"));
  //
  //    avatarCommandExecutor.addAvatarScrapStudyGroup(findStudyGroup, avatar);
  //  }
}
