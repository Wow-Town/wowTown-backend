package com.wowtown.wowtownbackend.privateSpace.domain;

import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
public class AvatarPrivateSpace {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "PRIVATE_SPACE_ID")
  private PrivateSpace privateSpace;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "AVATAR_ID")
  private Avatar avatar;

  private String roomName;

  private String sessionId;

  @Column(columnDefinition = "BINARY(16)")
  private UUID peerUUID;

  private LocalDateTime createAt;

  private LocalDateTime updateAt;

  protected AvatarPrivateSpace() {}

  public AvatarPrivateSpace(String roomName) {
    this.roomName = roomName;
    this.sessionId = null;
    this.createAt = LocalDateTime.now();
    this.updateAt = null;
  }

  public void setAvatar(Avatar avatar) {
    this.avatar = avatar;
  }

  public void setPrivateSpace(PrivateSpace privateSpace) {
    this.privateSpace = privateSpace;
  }

  public void setSession(String sessionId) {
    this.sessionId = sessionId;
    if (this.sessionId != null) {
      this.updateAt = LocalDateTime.now();
    }
  }

  public void setPeer(UUID peerUUID) {
    this.peerUUID = peerUUID;
    if (this.peerUUID != null) {
      this.updateAt = LocalDateTime.now();
    }
  }
}
