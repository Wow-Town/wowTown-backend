package com.wowtown.wowtownbackend.chatroom.domain;

import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Entity
@IdClass(AvatarChatRoom.class)
public class AvatarChatRoom implements Serializable {
  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CHATROOM_ID")
  private ChatRoom chatRoom;

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "AVATAR_ID")
  private Avatar avatar;

  private String customRoomName;

  private String defaultRoomName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CHAT_MESSAGE_ID")
  private ChatMessage lastCheckMessage;

  private String latestMessage;

  private Integer receiveMessageNum;

  private String sessionId;

  private boolean active;

  private LocalDateTime createAt;

  private LocalDateTime updateAt;

  protected AvatarChatRoom() {}

  public AvatarChatRoom(String customRoomName, String defaultRoomName) {
    this.customRoomName = customRoomName;
    this.defaultRoomName = defaultRoomName;
    this.lastCheckMessage = null;
    this.latestMessage = "";
    this.receiveMessageNum = 0;
    this.sessionId = null;
    this.active = false;
    this.createAt = LocalDateTime.now();
    this.updateAt = null;
  }

  public void setAvatar(Avatar avatar) {
    this.avatar = avatar;
  }

  public void setChatRoom(ChatRoom chatRoom) {
    this.chatRoom = chatRoom;
  }

  public void updateLastCheckMessage(ChatMessage chatMessage) {
    this.lastCheckMessage = chatMessage;
  }

  public void updateLatestMessage(String latestMessage) {
    this.latestMessage = latestMessage;
  }

  public void setSession(String sessionId) {
    this.sessionId = sessionId;
    if (this.sessionId != null) {
      this.updateAt = LocalDateTime.now();
    }
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public void increaseReceiveMessageNum() {
    this.receiveMessageNum++;
  }

  public void resetReceiveMessageNum() {
    this.receiveMessageNum = 0;
  }
}
