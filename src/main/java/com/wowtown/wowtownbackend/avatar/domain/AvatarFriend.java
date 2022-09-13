package com.wowtown.wowtownbackend.avatar.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
public class AvatarFriend {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Avatar avatar;

  @ManyToOne(fetch = FetchType.LAZY)
  private Avatar friend;

  @Enumerated(EnumType.STRING)
  private AvatarFriendStatus avatarFriendStatus;

  private LocalDateTime createAt;

  public AvatarFriend() {}

  public AvatarFriend(Avatar avatar, Avatar friendAvatar) {
    this.avatar = avatar;
    this.friend = friendAvatar;
    this.avatarFriendStatus = AvatarFriendStatus.REQUESTED;
    this.createAt = LocalDateTime.now();
  }

  public void approveFriendRequest() {
    this.avatarFriendStatus = AvatarFriendStatus.APPROVED;
  }
}
