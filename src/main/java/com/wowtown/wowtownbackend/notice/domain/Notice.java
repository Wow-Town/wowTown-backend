package com.wowtown.wowtownbackend.notice.domain;

import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.common.domain.Interest;
import lombok.Getter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Entity
public class Notice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String subject; // 이름보다 주제라는 용어가 괜찮은거 같다.
  private String description; // 스터디 그룹 설명
  private String randomPW;
  private UUID chatRoomUUID;

  @Enumerated(EnumType.STRING)
  private NoticeStatus noticeStatus;

  @ElementCollection
  @CollectionTable(name = "notice_interest")
  private Set<Interest> interestSet = new LinkedHashSet<>();

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

  protected Notice() {}

  public Notice(String subject, String description, Set<Interest> interestSet) {
    this.subject = subject;
    this.description = description;
    this.interestSet = interestSet;
    this.noticeStatus = NoticeStatus.OPEN;
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

  public void updateNotice(Notice notice) {
    this.subject = notice.subject;
    this.description = notice.description;
    this.noticeStatus = notice.noticeStatus;
  }

  public void addChatRoomInfo(UUID chatRoomUUID) {
    this.chatRoomUUID = chatRoomUUID;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Notice)) {
      return false;
    }
    Notice notice = (Notice) o;
    return this.id == notice.id
        && this.subject == notice.subject
        && this.description == notice.description;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.subject, this.description);
  }
}
