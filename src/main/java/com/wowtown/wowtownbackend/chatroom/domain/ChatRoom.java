package com.wowtown.wowtownbackend.chatroom.domain;

import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Entity
public class ChatRoom {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Integer participantsNum; // 채팅방 참여자 수

  private Integer currentJoinNum; // 현재 접속중인 인원수

  private UUID uuid;

  @Enumerated(EnumType.STRING)
  private ChatRoomType roomType;

  private LocalDateTime createAt;

  private LocalDateTime updateAt;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "CHATROOM_ID")
  private List<ChatMessage> chatMessageList = new ArrayList<>();

  @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
  private Set<AvatarChatRoom> avatarChatRoomSet = new HashSet<>();

  protected ChatRoom() {}

  public ChatRoom(ChatRoomType roomType) {
    this.participantsNum = 0;
    this.currentJoinNum = 0;
    this.uuid = UUID.randomUUID();
    this.roomType = roomType;
    this.createAt = LocalDateTime.now();
    this.updateAt = null;
  }

  public void addAvatarChatRoom(String roomName, Avatar participantAvatar) {
    AvatarChatRoom avatarChatRoom = new AvatarChatRoom(roomName, roomName);
    avatarChatRoom.setAvatar(participantAvatar);
    avatarChatRoom.setChatRoom(this);
    this.avatarChatRoomSet.add(avatarChatRoom);
    this.participantsNum++;
  }

  public void enterChatRoom(String sessionId, long enterAvatarId) {
    for (AvatarChatRoom avatarChatRoom : this.avatarChatRoomSet) {
      if (avatarChatRoom.getAvatar().getId() == enterAvatarId) {
        if (this.roomType == ChatRoomType.MULTI && !avatarChatRoom.isActive()) { // 단톡에 처음 입장했을 경우
          this.currentJoinNum++;
          avatarChatRoom.setActive(true);
          avatarChatRoom.setSession(sessionId);
          return;
        }

        // 마지막 입장한 시점을 기준으로 이후에 생성된 메시지를 확인하여 읽음 표시해줌
        ChatMessage lastCheckMessage = avatarChatRoom.getLastCheckMessage();
        if (lastCheckMessage != null) {
          List<ChatMessage> toUpdateMessage =
              this.chatMessageList.stream()
                  .filter(
                      chatMessage -> chatMessage.getSendAt().isAfter(lastCheckMessage.getSendAt()))
                  .collect(Collectors.toList());

          for (ChatMessage chatMessage : toUpdateMessage) {
            chatMessage.decreaseCount();
          }
        } else {
          for (ChatMessage chatMessage : this.chatMessageList) {
            chatMessage.decreaseCount();
          }
        }
        // 아바타 채팅방 읽지 않은 메시지 수를 0 으로 초기화해준다.
        avatarChatRoom.resetReceiveMessageNum();
        // 아바타 채팅방 세션 생성하여 마지막 입장 시간은 업데이트해줌
        avatarChatRoom.setSession(sessionId);
        this.currentJoinNum++;
        break;
      }
    }
  }

  public void leaveChatRoom(String sessionId) {
    for (AvatarChatRoom avatarChatRoom : this.avatarChatRoomSet) {
      if (avatarChatRoom.getSessionId() != null) {
        if (avatarChatRoom.getSessionId().equals(sessionId)) {
          // 채팅방 나갈때 마지막 메시지를 업데이트 해준다.
          int lastIdx = this.chatMessageList.size() - 1;
          ChatMessage latestMessage = this.chatMessageList.get(lastIdx);
          avatarChatRoom.updateLastCheckMessage(latestMessage);

          // 아바타 채팅방 세션을 삭제한다.
          avatarChatRoom.setSession(null);
          this.currentJoinNum--;
          break;
        }
      }
    }
  }

  public ChatMessage addChatMessage(ChatMessage payload) {
    for (AvatarChatRoom avatarChatRoom : this.avatarChatRoomSet) {
      if (avatarChatRoom.getSessionId() == null) {
        avatarChatRoom.increaseReceiveMessageNum();
      }
      avatarChatRoom.setActive(true);
      avatarChatRoom.updateLatestMessage(payload.getMessage());
    }
    this.chatMessageList.add(payload);
    this.updateAt = LocalDateTime.now();
    return payload;
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
    return this.id == chatRoom.id && this.uuid == chatRoom.uuid;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.uuid);
  }
}
