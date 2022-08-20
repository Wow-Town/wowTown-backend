package com.wowtown.wowtownbackend.chatroom.application;

import com.wowtown.wowtownbackend.avatar.application.AvatarCommandExecutor;
import com.wowtown.wowtownbackend.avatar.application.common.AvatarProvider;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.chatroom.application.common.ChatRoomMapper;
import com.wowtown.wowtownbackend.chatroom.application.dto.request.ChatMessageDto;
import com.wowtown.wowtownbackend.chatroom.application.dto.request.InviteAvatar;
import com.wowtown.wowtownbackend.chatroom.application.dto.response.GetCreatedChatRoomDto;
import com.wowtown.wowtownbackend.chatroom.domain.*;
import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
import com.wowtown.wowtownbackend.studyGroup.application.StudyGroupCommandExecutor;
import com.wowtown.wowtownbackend.studyGroup.application.common.StudyGroupProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomCommandExecutor {
  private final AvatarProvider avatarProvider;
  private final StudyGroupProvider studyGroupProvider;
  private final ChatRoomRepository chatRoomRepository;
  private final AvatarChatRoomRepository avatarChatRoomRepository;
  private final ChatRoomMapper chatRoomMapper;
  private final AvatarCommandExecutor avatarCommandExecutor;
  private final StudyGroupCommandExecutor studyGroupCommandExecutor;
  private final SimpMessageSendingOperations sendingOperations;

  @Transactional
  public GetCreatedChatRoomDto createAvatarChatroom(InviteAvatar dto, Avatar avatar) {
    // 초대한 아바타를 참여한 아바타 리스트를 만든다.
    Avatar invitedAvatar = avatarProvider.getAvatar(dto.getAvatarId());

    ChatRoomType chatRoomType = ChatRoomType.SINGLE;

    // 채팅방을 만든다.
    ChatRoom chatRoom = new ChatRoom(chatRoomType);

    chatRoom.addAvatarChatRoom(invitedAvatar.getNickName(), avatar);
    chatRoom.addAvatarChatRoom(avatar.getNickName(), invitedAvatar);

    chatRoomRepository.save(chatRoom);

    return chatRoomMapper.toGetCreatedChatRoomDto(invitedAvatar.getNickName(), chatRoom.getUuid());
  }

  //  @Transactional
  //  public GetCreatedChatRoomDto createStudyGroupChatroom(Long studyGroupId) {
  //
  //    StudyGroup findStudyGroup = studyGroupProvider.getStudyGroup(studyGroupId);
  //
  //    ChatRoom chatRoom =
  //        chatRoomRepository.save(
  //            chatRoomMapper.toStudyGroupChatRoom(
  //                findStudyGroup.getSubject(), findStudyGroup.getPersonnel()));
  //
  //    // 스터디그룹 채팅방 설정
  //    studyGroupCommandExecutor.addChatRoomInfo(findStudyGroup, chatRoom.getUuid());
  //
  //    return chatRoomMapper.toGetCreatedChatRoomDto(chatRoom.getUuid());
  //  }

  @Transactional
  public void enterChatRoom(ChatMessageDto message) {
    ChatRoom findChatRoom =
        chatRoomRepository
            .findChatRoomByUuid(message.getChatRoomUUID())
            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 채팅방 입니다."));

    findChatRoom.enterChatRoom(message.getSessionId(), message.getSenderId());
    sendingOperations.convertAndSend(
        "/sub/chatRooms/" + message.getChatRoomUUID().toString(), message);
  }

  @Transactional
  public void leaveChatRoom(ChatMessageDto message) {
    Optional<AvatarChatRoom> findAvatarChatroom =
        avatarChatRoomRepository.findAvatarChatRoomWithSessionId(message.getSessionId());

    findAvatarChatroom.ifPresent(
        avatarChatRoom -> avatarChatRoom.getChatRoom().leaveChatRoom(message.getSessionId()));
  }

  @Transactional
  public void sendChatMessage(ChatMessageDto message) {
    ChatRoom findChatRoom =
        chatRoomRepository
            .findChatRoomByUuid(message.getChatRoomUUID())
            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 채팅방 입니다."));
    int count = findChatRoom.getParticipantsNum() - findChatRoom.getCurrentJoinNum();
    ChatMessage chatMessage =
        chatRoomMapper.toChatMessage(
            message.getType(),
            message.getSender(),
            message.getSenderId(),
            message.getMessage(),
            count);

    ChatMessage chatMessageToSend = findChatRoom.addChatMessage(chatMessage);

    sendingOperations.convertAndSend(
        "/sub/chatRooms/" + message.getChatRoomUUID().toString(),
        chatRoomMapper.toGetChatMessageDto(chatMessageToSend));

    //    ChatRoom findChatRoom =
    //        chatRoomRepository
    //            .findChatRoomByUuid(chatRoomUUID)
    //            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 채팅방 입니다."));
    //
    //    findChatRoom.increaseParticipantsNum();

    // 아바타가 참여중인 채팅방 목록에 추가
    // avatarCommandExecutor.avatarEnterChatRoom(findChatRoom, avatar);
  }

  //  @Transactional
  //  public void leaveChatRoom(ChatMessageDto message) {
  //    ChatRoom findChatRoom =
  //        chatRoomRepository
  //            .findChatRoomByUuid(chatRoomUUID)
  //            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 채팅방 입니다."));
  //
  //    findChatRoom.decreaseParticipantsNum();
  //
  //    // 아바타가 참여중인 채팅방 목록에서 제거
  //    avatarCommandExecutor.avatarLeaveChatRoom(findChatRoom, avatar);
  //  }
  //
  //  @Transactional
  //  public boolean updateChatRoom(UUID chatRoomUUID, String roomName, int personnel) {
  //    ChatRoom findChatRoom =
  //        chatRoomRepository
  //            .findChatRoomByUuid(chatRoomUUID)
  //            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 채팅방 입니다."));
  //
  //    findChatRoom.updateChatRoom(roomName, personnel);
  //    return true;
  //  }
}
