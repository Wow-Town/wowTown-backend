package com.wowtown.wowtownbackend.avatar.domain;

import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.common.domain.Interest;
import com.wowtown.wowtownbackend.notice.domain.Notice;
import com.wowtown.wowtownbackend.user.domain.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Entity
public class Avatar {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nickName;

  private String description;

  private String inviteCode;

  // 현재는 의상에 별도의 상세정보가 없어 embedding 시킨다.
  private Integer costumeIdx;

  private LocalDateTime createAt;

  private LocalDateTime updateAt;

  @Enumerated(EnumType.STRING)
  @ElementCollection
  @CollectionTable(name = "avatar_interest")
  private Set<Interest> interestSet = new HashSet<>();

  @OneToMany(mappedBy = "avatar")
  private Set<Notice> avatarScrapNoticeSet = new HashSet<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_ID")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "Channel_ID")
  private Channel channel;

  @OneToMany(mappedBy = "avatar", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<AvatarFriend> avatarFriendSet = new LinkedHashSet<>();

  protected Avatar() {}

  public Avatar(String nickName, String description, Integer costumeIdx) {
    this.nickName = nickName;
    this.description = description;
    this.costumeIdx = costumeIdx;
    this.createAt = LocalDateTime.now();
    this.updateAt = null;
  }

  public Avatar(
      String nickName, String description, Integer costumeIdx, User user, Channel channel) {
    this.nickName = nickName;
    this.description = description;
    this.costumeIdx = costumeIdx;
    this.user = user;
    this.channel = channel;
    this.createAt = LocalDateTime.now();
    this.updateAt = null;
  }

  public void addInterest(Interest payload) {
    this.interestSet.add(payload);
  }

  public void updateAvatar(Avatar updatePayload) {
    this.nickName = updatePayload.getNickName();
    this.description = updatePayload.getDescription();
    this.interestSet = updatePayload.getInterestSet();
    this.costumeIdx = updatePayload.getCostumeIdx();
    this.updateAt = LocalDateTime.now();
  }

  public void updateInviteCode(String code) {
    this.inviteCode = code;
  }

  //  public void addAvatarChatRoom(ChatRoom payload) {
  //    AvatarChatRoom avatarChatRoom = new AvatarChatRoom(payload.getRoomName());
  //    avatarChatRoom.setAvatar(this);
  //    avatarChatRoom.setChatRoom(payload);
  //    this.avatarChatRoomList.add(avatarChatRoom);
  //  }

  public void addAvatarFriend(AvatarFriend payload) {
    this.avatarFriendSet.add(payload);
  }

  public AvatarFriendStatus checkFriendInAvatarFriendSet(Avatar friend) {
    for (AvatarFriend avatarFriend : this.avatarFriendSet) {
      if (avatarFriend.getAvatar().equals(this) && avatarFriend.getFriend().equals(friend)) {
        return avatarFriend.getAvatarFriendStatus();
      }
    }
    return AvatarFriendStatus.BLANK;
  }

  public void approveAvatarFriend(Avatar friend) {
    for (AvatarFriend avatarFriend : this.avatarFriendSet) {
      if (avatarFriend.getAvatar().equals(this) && avatarFriend.getFriend().equals(friend)) {
        avatarFriend.approveFriendRequest();
        break;
      }
    }
  }

  public void rejectAvatarFriend(Avatar friend) {
    this.avatarFriendSet.removeIf(
        avatarFriend ->
            avatarFriend.getAvatar().equals(this) && avatarFriend.getFriend().equals(friend));
  }

  //  public void addAvatarScrapStudyGroup(Notice payload) {
  //    this.avatarScrapNoticeSet.add(payload);
  //  }
  //
  //  public void removeAvatarScrapStudyGroup(Notice payload) {
  //    this.avatarScrapNoticeSet.remove(payload);
  //  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Avatar)) {
      return false;
    }
    Avatar avatar = (Avatar) o;
    return this.id == avatar.id
        && this.nickName == avatar.nickName
        && this.description == avatar.description;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.nickName, this.description);
  }
}
