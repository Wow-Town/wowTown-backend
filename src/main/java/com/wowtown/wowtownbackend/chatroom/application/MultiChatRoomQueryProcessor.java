package com.wowtown.wowtownbackend.chatroom.application;

import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.chatroom.application.common.MultiChatRoomMapper;
import com.wowtown.wowtownbackend.chatroom.application.dto.response.GetMultiChatRoomDto;
import com.wowtown.wowtownbackend.chatroom.domain.MultiChatRoom;
import com.wowtown.wowtownbackend.chatroom.domain.MultiChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service

@RequiredArgsConstructor
public class MultiChatRoomQueryProcessor {
    private final MultiChatRoomRepository multiChatRoomRepository;
    private final MultiChatRoomMapper multiChatRoomMapper;

    public GetMultiChatRoomDto getMultiChatRoom(Long multiChatRoomId
                                                // , Avatar avatar,Long StudyGroupId
                                                ){
        MultiChatRoom findMultiChatRoom =
                multiChatRoomRepository
                        .findById(multiChatRoomId)
                        .orElseThrow(() -> new IllegalArgumentException("공고채팅방이 존재하지 않습니다."));
        return multiChatRoomMapper.toGetMultiChatRoomDto(findMultiChatRoom);
    }
    public List<GetMultiChatRoomDto> getMultiChatRoomsByAvatar(Long channelId,Avatar avatar){

        List<GetMultiChatRoomDto> findMultiChatRooms =
                multiChatRoomRepository
                        .findByChannelIdAndAvatarId(channelId, avatar.getId())
                        .stream()
                        .map(multiChatRoom ->multiChatRoomMapper.toGetMultiChatRoomDto(multiChatRoom))
                        .collect(Collectors.toList());

        return findMultiChatRooms;
    }




}
