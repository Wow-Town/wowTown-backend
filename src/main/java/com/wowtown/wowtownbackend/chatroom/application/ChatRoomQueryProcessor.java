// package com.wowtown.wowtownbackend.chatroom.application;
//
// import com.wowtown.wowtownbackend.avatar.application.AvatarQueryProcessor;
// import com.wowtown.wowtownbackend.avatar.domain.Avatar;
// import com.wowtown.wowtownbackend.chatroom.application.common.ChatRoomMapper;
// import com.wowtown.wowtownbackend.chatroom.application.dto.response.GetChatRoomDetailDto;
// import com.wowtown.wowtownbackend.chatroom.application.dto.response.GetChatRoomDto;
// import com.wowtown.wowtownbackend.chatroom.domain.ChatRoom;
// import com.wowtown.wowtownbackend.chatroom.domain.ChatRoomRepository;
// import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
// import lombok.RequiredArgsConstructor;
// import org.springframework.stereotype.Service;
//
// import java.util.List;
// import java.util.UUID;
// import java.util.stream.Collectors;
//
// @Service
// @RequiredArgsConstructor
// public class ChatRoomQueryProcessor {
//  private final ChatRoomRepository chatRoomRepository;
//  private final ChatRoomMapper chatRoomMapper;
//  private final AvatarQueryProcessor avatarQueryProcessor;
//
//  public GetChatRoomDetailDto getChatRoom(UUID chatRoomUUID) {
//    ChatRoom findChatRoom =
//        chatRoomRepository
//            .findChatRoomByUuid(chatRoomUUID)
//            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 채팅방 입니다."));
//
//    List<Avatar> avatarList = avatarQueryProcessor.getAvatarWithChatRoomUUID(chatRoomUUID);
//    return chatRoomMapper.toGetChatRoomDetailDto(findChatRoom, avatarList);
//  }
//
//  public List<GetChatRoomDto> getChatRoomListByAvatar(Avatar avatar) {
//
//    List<GetChatRoomDto> findChatRooms =
//        avatarQueryProcessor.getAvatarChatRoomWithAvatar(avatar).stream()
//            .map(
//                avatarChatRoom ->
//                    chatRoomMapper.toGetChatRoomDto(
//                        avatarChatRoom.getChatRoom().getUuid(),
//                        avatarChatRoom.getCustomRoomName(),
//                        avatarChatRoom.getChatRoom().getChatRoomType()))
//            .collect(Collectors.toList());
//
//    return findChatRooms;
//  }
// }
