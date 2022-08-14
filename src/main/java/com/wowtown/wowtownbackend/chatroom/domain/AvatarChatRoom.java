package com.wowtown.wowtownbackend.chatroom.domain;

import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
public class AvatarChatRoom {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  private String customRoomName;

  private String defaultRoomName;

  @ManyToOne
  @JoinColumn(name = "CHAT_MESSAGE_ID")
  private ChatMessage lastCheckMessage;

  private String latestMessage;

  private Integer receiveMessageNum;

  private String sessionId;

  private boolean active;

  private LocalDateTime createAt;

  private LocalDateTime updateAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CHATROOM_ID")
  private ChatRoom chatRoom;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "AVATAR_ID")
  private Avatar avatar;

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
