package com.wowtown.wowtownbackend.privateSpace.domain;

import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.notice.domain.Notice;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Entity
public class PrivateSpace {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Integer participantsNum; // 프라이빗 스페이스 참여자 수

  private Integer currentJoinNum; // 현재 접속중인 인원수

  @Column(columnDefinition = "BINARY(16)")
  private UUID uuid;

  private LocalDateTime createAt;

  private LocalDateTime updateAt;

  @OneToMany(mappedBy = "privateSpace", cascade = CascadeType.ALL)
  private Set<AvatarPrivateSpace> avatarPrivateSpaceSet = new HashSet<>();

  @OneToOne(mappedBy = "privateSpace")
  private Notice notice;

  protected PrivateSpace() {}

  public PrivateSpace(Notice notice) {
    this.participantsNum = 0;
    this.currentJoinNum = 0;
    this.uuid = UUID.randomUUID();
    this.createAt = LocalDateTime.now();
    this.updateAt = null;
    this.notice = notice;
  }

  public void addAvatarPrivateSpace(String roomName, Avatar participantAvatar) {
    AvatarPrivateSpace avatarPrivateSpace = new AvatarPrivateSpace(roomName);
    avatarPrivateSpace.setAvatar(participantAvatar);
    avatarPrivateSpace.setPrivateSpace(this);
    this.avatarPrivateSpaceSet.add(avatarPrivateSpace);
    this.participantsNum++;
  }

  public void enterPrivateSpace(String sessionId, long enterAvatarId, UUID peerUUID) {
    for (AvatarPrivateSpace avatarPrivateSpace : this.avatarPrivateSpaceSet) {
      if (avatarPrivateSpace.getAvatar().getId() == enterAvatarId) {
        // 아바타 프라이빗 스페이스 세션 생성하여 마지막 입장 시간은 업데이트해줌
        avatarPrivateSpace.setSession(sessionId);
        avatarPrivateSpace.setPeer(peerUUID);
        this.currentJoinNum++;
        break;
      }
    }
  }

  public void leavePrivateSpace(String sessionId) {
    for (AvatarPrivateSpace avatarPrivateSpace : this.avatarPrivateSpaceSet) {
      if (avatarPrivateSpace.getSessionId() != null) {
        if (avatarPrivateSpace.getSessionId().equals(sessionId)) {
          // 아바타 프라이빗 스페이스 세션을 삭제한다.
          avatarPrivateSpace.setSession(null);
          avatarPrivateSpace.setPeer(null);
          this.currentJoinNum--;
          break;
        }
      }
    }
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof PrivateSpace)) {
      return false;
    }
    PrivateSpace privateSpace = (PrivateSpace) o;
    return this.id == privateSpace.id && this.uuid == privateSpace.uuid;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.uuid);
  }
}
