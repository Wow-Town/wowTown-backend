package com.wowtown.wowtownbackend.chatroom.application;

import com.wowtown.wowtownbackend.avatar.application.AvatarCommandExecutor;
import com.wowtown.wowtownbackend.avatar.application.common.AvatarProvider;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.chatroom.application.common.ChatRoomMapper;
import com.wowtown.wowtownbackend.chatroom.application.dto.response.GetCreatedChatRoomDto;
import com.wowtown.wowtownbackend.chatroom.domain.ChatRoom;
import com.wowtown.wowtownbackend.chatroom.domain.ChatRoomRepository;
import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
import com.wowtown.wowtownbackend.notice.application.NoticeCommandExecutor;
import com.wowtown.wowtownbackend.notice.application.common.NoticeProvider;
import com.wowtown.wowtownbackend.notice.domain.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomCommandExecutor {
  private final AvatarProvider avatarProvider;
  private final NoticeProvider noticeProvider;
  private final ChatRoomRepository chatRoomRepository;
  private final ChatRoomMapper chatRoomMapper;
  private final AvatarCommandExecutor avatarCommandExecutor;
  private final NoticeCommandExecutor noticeCommandExecutor;

  @Transactional
  public GetCreatedChatRoomDto createAvatarChatroom(Long avatarId) {

    Avatar findAvatar = avatarProvider.getAvatar(avatarId);

    ChatRoom chatRoom =
        chatRoomRepository.save(chatRoomMapper.toAvatarChatRoom(findAvatar.getNickName()));

    return chatRoomMapper.toGetCreatedChatRoomDto(chatRoom.getUuid());
  }

  @Transactional
  public GetCreatedChatRoomDto createStudyGroupChatroom(Long studyGroupId) {

    Notice findNotice = noticeProvider.getNotice(studyGroupId);

    ChatRoom chatRoom =
        chatRoomRepository.save(
            chatRoomMapper.toStudyGroupChatRoom(
                findNotice.getSubject(), findNotice.getPersonnel()));

    // 스터디그룹 채팅방 설정
    noticeCommandExecutor.addChatRoomInfo(findNotice, chatRoom.getUuid());

    return chatRoomMapper.toGetCreatedChatRoomDto(chatRoom.getUuid());
  }

  @Transactional
  public void enterChatRoom(UUID chatRoomUUID, Avatar avatar) {
    ChatRoom findChatRoom =
        chatRoomRepository
            .findChatRoomByUuid(chatRoomUUID)
            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 채팅방 입니다."));

    findChatRoom.increaseParticipantsNum();

    // 아바타가 참여중인 채팅방 목록에 추가
    avatarCommandExecutor.avatarEnterChatRoom(findChatRoom, avatar);
  }

  @Transactional
  public void leaveChatRoom(UUID chatRoomUUID, Avatar avatar) {
    ChatRoom findChatRoom =
        chatRoomRepository
            .findChatRoomByUuid(chatRoomUUID)
            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 채팅방 입니다."));

    findChatRoom.decreaseParticipantsNum();

    // 아바타가 참여중인 채팅방 목록에서 제거
    avatarCommandExecutor.avatarLeaveChatRoom(findChatRoom, avatar);
  }

  @Transactional
  public boolean updateChatRoom(UUID chatRoomUUID, String roomName, int personnel) {
    ChatRoom findChatRoom =
        chatRoomRepository
            .findChatRoomByUuid(chatRoomUUID)
            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 채팅방 입니다."));

    findChatRoom.updateChatRoom(roomName, personnel);
    return true;
  }
}
