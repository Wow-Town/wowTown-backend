package com.wowtown.wowtownbackend.studyGroup.domain;

import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class AvatarStudyGroup {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Enumerated(EnumType.STRING)
  private StudyGroupRole role;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "avatar_id")
  private Avatar avatar;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "study_group_id")
  private StudyGroup studyGroup;

  protected AvatarStudyGroup() {}

  public AvatarStudyGroup(StudyGroupRole role) {
    this.role = role;
  }

  public void setAvatar(Avatar avatar) {
    this.avatar = avatar;
  }

  public void setStudyGroup(StudyGroup studyGroup) {
    this.studyGroup = studyGroup;
  }
}
