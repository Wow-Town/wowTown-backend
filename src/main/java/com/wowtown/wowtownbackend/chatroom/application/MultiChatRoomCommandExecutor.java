package com.wowtown.wowtownbackend.chatroom.application;


import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.avatar.domain.AvatarRepository;
import com.wowtown.wowtownbackend.chatroom.application.common.MultiChatRoomMapper;
import com.wowtown.wowtownbackend.chatroom.application.dto.request.CreateMultiChatRoomDto;
import com.wowtown.wowtownbackend.chatroom.application.dto.request.UpdateMultiChatRoomDto;
import com.wowtown.wowtownbackend.chatroom.domain.MultiChatRoom;
import com.wowtown.wowtownbackend.chatroom.domain.MultiChatRoomRepository;
import com.wowtown.wowtownbackend.studyGroup.application.StudyGroupQueryProcessor;
import com.wowtown.wowtownbackend.studyGroup.domain.AvatarStudyGroup;
import com.wowtown.wowtownbackend.studyGroup.domain.AvatarStudyGroupRepository;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroup;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MultiChatRoomCommandExecutor {
    private final MultiChatRoomRepository multiChatRoomRepository;
    private final MultiChatRoomMapper multiChatRoomMapper;
    private final StudyGroupQueryProcessor studyGroupQueryProcessor;

   //Create와 Update는 방장만이, Delete는 cascade + 추가로 수시로 방장이 삭제할 수 있게

    @Transactional
    public long createMultiChatRoom(
            Long studyGroupId,
            CreateMultiChatRoomDto dto) //
    {
        StudyGroup findStudyGroup = studyGroupQueryProcessor.getStudyGroupById(studyGroupId);
        MultiChatRoom multiChatRoom =
                multiChatRoomRepository.save(multiChatRoomMapper.toMultiChatRoom(dto.getName(),dto.getPersonnel(),findStudyGroup));
        return multiChatRoom.getId();
    }
    @Transactional
    public boolean updateMultiChatRoom(
            UpdateMultiChatRoomDto dto,
            long multiChatRoomId)
    {
        MultiChatRoom findMultiChatRoom =
                multiChatRoomRepository.findById(multiChatRoomId)
                        .orElseThrow(()->new IllegalArgumentException("존재하지 않는 공고 채팅방입니다."));
        MultiChatRoom updateMultiChatRoom = multiChatRoomMapper.toUpdateMultiChatRoom(dto.getName(),dto.getPersonnel());
        findMultiChatRoom.updateMultiChatRoom(updateMultiChatRoom);
        return true;
    }
    @Transactional
    public boolean deleteMultiChatRoom(Long studyGroupId, Long multiChatRoomId,Avatar avatar){


        MultiChatRoom findMultiChatRoom =
                multiChatRoomRepository
                        .findById(multiChatRoomId)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공고 채팅방입니다"));
        multiChatRoomRepository.delete(findMultiChatRoom);
        return true;
    }


}
