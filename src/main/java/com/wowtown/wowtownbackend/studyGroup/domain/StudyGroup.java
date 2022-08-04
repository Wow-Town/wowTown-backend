package com.wowtown.wowtownbackend.studyGroup.domain;

import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.common.domain.Interest;
import lombok.Getter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Entity
public class StudyGroup {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String subject; // 이름보다 주제라는 용어가 괜찮은거 같다.
  private int personnel; // 구하는 인원수
  private String description; // 스터디 그룹 설명
  private String randomPW;
  private UUID chatRoomUUID;

  @Enumerated(EnumType.STRING)
  private StudyGroupStatus studyGroupStatus;

  @ElementCollection
  @CollectionTable(name = "study_group_interest")
  private Set<Interest> interestSet = new HashSet<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "Channel_ID")
  private Channel channel;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "Avatar_ID")
  private Avatar avatar;

  //  @OneToOne(fetch = FetchType.LAZY)
  //  @JoinColumn(name = "PRIVATE_SPACE_ID")
  //  private PrivateSpace privateSpace;
  //

  protected StudyGroup() {}

  public StudyGroup(
      String subject,
      Integer personnel,
      String description,
      Set<Interest> interestSet,
      StudyGroupStatus studyGroupStatus) {
    this.subject = subject;
    this.personnel = personnel;
    this.description = description;
    this.interestSet = interestSet;
    this.studyGroupStatus = studyGroupStatus;
  }

  public void setDefaultPW(String randomPW) {
    this.randomPW = randomPW;
  }

  public void addOwner(Avatar avatar) {
    this.avatar = avatar;
    this.channel = avatar.getChannel();
  }

  public void addChatRoomUUID(UUID chatRoomUUID) {
    this.chatRoomUUID = chatRoomUUID;
  }

  public boolean isSameOwner(Avatar avatar) {
    if (this.avatar.equals(avatar)) {
      return true;
    }
    return false;
  }

  public void updateStudyGroup(StudyGroup studyGroup) {
    this.subject = studyGroup.subject;
    this.personnel = studyGroup.personnel;
    this.description = studyGroup.description;
    this.studyGroupStatus = studyGroup.studyGroupStatus;
  }

  public void addChatRoomInfo(UUID chatRoomUUID) {
    this.chatRoomUUID = chatRoomUUID;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof StudyGroup)) {
      return false;
    }
    StudyGroup studyGroup = (StudyGroup) o;
    return this.id == studyGroup.id
        && this.subject == studyGroup.subject
        && this.personnel == studyGroup.personnel
        && this.description == studyGroup.description;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.subject, this.personnel, this.description);
  }
}
