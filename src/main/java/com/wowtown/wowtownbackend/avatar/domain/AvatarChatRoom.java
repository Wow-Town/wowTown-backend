package com.wowtown.wowtownbackend.avatar.domain;

import com.wowtown.wowtownbackend.chatroom.domain.ChatRoom;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class AvatarChatRoom {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  private String customRoomName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CHATROOM_ID")
  private ChatRoom chatRoom;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "AVATAR_ID")
  private Avatar avatar;

  protected AvatarChatRoom() {}

  public AvatarChatRoom(String customRoomName) {
    this.customRoomName = customRoomName;
  }

  public void setAvatar(Avatar avatar) {
    this.avatar = avatar;
  }

  public void setChatRoom(ChatRoom chatRoom) {
    this.chatRoom = chatRoom;
  }
}
