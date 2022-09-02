package com.wowtown.wowtownbackend.chatroom.application;

import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.chatroom.application.common.ChatRoomMapper;
import com.wowtown.wowtownbackend.chatroom.application.dto.response.GetChatRoomDetailDto;
import com.wowtown.wowtownbackend.chatroom.application.dto.response.GetChatRoomDto;
import com.wowtown.wowtownbackend.chatroom.domain.AvatarChatRoomRepository;
import com.wowtown.wowtownbackend.chatroom.domain.ChatRoom;
import com.wowtown.wowtownbackend.chatroom.domain.ChatRoomRepository;
import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomQueryProcessor {
  private final ChatRoomRepository chatRoomRepository;
  private final AvatarChatRoomRepository avatarChatRoomRepository;
  private final ChatRoomMapper chatRoomMapper;

  public GetChatRoomDetailDto getChatRoom(UUID chatRoomUUID) {
    ChatRoom findChatRoom =
        chatRoomRepository
            .findChatRoomByUuid(chatRoomUUID)
            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 채팅방 입니다."));

    return chatRoomMapper.toGetChatRoomDetailDto(findChatRoom);
  }

  public List<GetChatRoomDto> getChatRoomListByAvatar(Avatar avatar) {
    List<GetChatRoomDto> findAvatarChatRooms =
        avatarChatRoomRepository.findAvatarChatRoomByAvatarId(avatar.getId()).stream()
            .map(
                avatarChatRoom ->
                    chatRoomMapper.toGetChatRoomDto(
                        avatarChatRoom.getChatRoom().getUuid(),
                        avatarChatRoom.getCustomRoomName(),
                        avatarChatRoom.getLatestMessage(),
                        avatarChatRoom.getReceiveMessageNum(),
                        avatarChatRoom.getChatRoom().getParticipantsNum(),
                        avatarChatRoom.getChatRoom().getRoomType().toString()))
            .collect(Collectors.toList());

    return findAvatarChatRooms;
  }
}
