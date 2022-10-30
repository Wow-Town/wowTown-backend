package com.wowtown.wowtownbackend.chatroom.domain;

import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class AvatarChatRoom implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CHATROOM_ID")
  private ChatRoom chatRoom;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "AVATAR_ID")
  private Avatar avatar;

  private String customRoomName;

  private String defaultRoomName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CHAT_MESSAGE_ID")
  private ChatMessage lastCheckMessage;

  @Lob private byte[] latestMessage;

  private Integer receiveMessageNum;

  @ElementCollection
  @CollectionTable(name = "avatar_chat_room_session")
  private List<String> sessionList = new ArrayList<>();

  private boolean active;

  private LocalDateTime createAt;

  private LocalDateTime updateAt;

  protected AvatarChatRoom() {}

  public AvatarChatRoom(String customRoomName, String defaultRoomName) {
    this.customRoomName = customRoomName;
    this.defaultRoomName = defaultRoomName;
    this.lastCheckMessage = null;
    this.latestMessage = null;
    this.receiveMessageNum = 0;
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

  public void updateLatestMessage(byte[] latestMessage) {
    this.latestMessage = latestMessage;
  }

  public void addSession(String sessionId) {
    if (!this.sessionList.contains(sessionId)) {
      this.sessionList.add(sessionId);
    }
    this.updateAt = LocalDateTime.now();
  }

  public void removeSession(String sessionId) {
    this.sessionList.remove(sessionId);
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
