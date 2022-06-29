package com.wowtown.wowtownbackend.studyGroup.domain;

import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.common.domain.InterestType;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class StudyGroup {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String subject; // 이름보다 주제라는 용어가 괜찮은거 같다.
  private int personnel; // 구하는 인원수
  private String description; // 스터디 그룹 설명

  @Enumerated(EnumType.STRING)
  private StudyGroupStatus status;

  @ElementCollection
  @Enumerated(EnumType.STRING)
  List<InterestType> interestTypes = new ArrayList<>();

  /*@OneToMany(mappedBy = "studyGroup", cascade = CascadeType.ALL)
  private List<StudyGroupInterestTypes> studyGroupInterestTypes = new ArrayList<>();*/

  //  @OneToOne(fetch = FetchType.LAZY)
  //  @JoinColumn(name = "PRIVATE_SPACE_ID")
  //  private PrivateSpace privateSpace;
  //
  //  @OneToOne(fetch = FetchType.LAZY)
  //  @JoinColumn(name = "MULTI_CHAT_ROOM_ID")
  //  private MultiChatRoom openChatRoom;
  //
  @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.ALL)
  private List<AvatarStudyGroup> avatarStudyGroupList = new ArrayList<>();

  protected StudyGroup() {}

  public StudyGroup(
      String subject, Integer personnel, String description, StudyGroupStatus status) {
    this.subject = subject;
    this.personnel = personnel;
    this.description = description;
    this.status = status;
  }

  public void updateStudyGroup(StudyGroup studyGroup) {
    this.subject = studyGroup.subject;
    this.personnel = studyGroup.personnel;
    this.description = studyGroup.description;
    this.status = studyGroup.status;
  }

  public void addAvatarStudyGroup(Avatar avatar, StudyGroupRole isHost) {
    AvatarStudyGroup avatarStudyGroup = new AvatarStudyGroup(isHost);
    avatarStudyGroup.setAvatar(avatar);
    avatarStudyGroup.setStudyGroup(this);
    this.avatarStudyGroupList.add(avatarStudyGroup);
  }

  public boolean checkAvatarStudyGroupRoleIsHost(Avatar avatar) {
    for (AvatarStudyGroup avatarStudyGroup : this.avatarStudyGroupList) { // 뭔가 이 로직이 최선인가?
      if (avatarStudyGroup.getAvatar().equals(avatar)) {
        if (avatarStudyGroup.getRole() == StudyGroupRole.HOST) {
          return true;
        }
      }
    }
    return false;
  }
}
