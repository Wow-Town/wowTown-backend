package com.wowtown.wowtownbackend.chatroom.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Getter
@Entity
public class ChatRoom {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String roomName;
  private int personnel; // 최대 인원수
  private int participantsNum; // 현재 참여자 수

  private UUID uuid;

  @Enumerated(EnumType.STRING)
  private ChatRoomType chatRoomType;

  protected ChatRoom() {}

  public ChatRoom(String roomName) {
    this.roomName = roomName;
    this.personnel = 1;
    this.participantsNum = 0;
    this.uuid = UUID.randomUUID();
    this.chatRoomType = ChatRoomType.SINGLE;
  }

  public ChatRoom(String roomName, int personnel) {
    this.roomName = roomName;
    this.personnel = personnel;
    this.participantsNum = 0;
    this.uuid = UUID.randomUUID();
    this.chatRoomType = ChatRoomType.MULTI;
  }

  public void updateChatRoom(String roomName, int personnel) {
    this.roomName = roomName;
    this.personnel = personnel;
  }

  public void increaseParticipantsNum() {
    this.participantsNum += 1;
  }

  public void decreaseParticipantsNum() {
    this.participantsNum -= 1;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof ChatRoom)) {
      return false;
    }
    ChatRoom chatRoom = (ChatRoom) o;
    return this.id == chatRoom.id
        && this.uuid == chatRoom.uuid
        && this.chatRoomType == chatRoom.chatRoomType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.uuid, this.chatRoomType);
  }
}
