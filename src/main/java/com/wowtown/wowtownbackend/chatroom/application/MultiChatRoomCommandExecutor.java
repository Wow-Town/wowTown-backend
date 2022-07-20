package com.wowtown.wowtownbackend.chatroom.application;


import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.chatroom.application.common.MultiChatRoomMapper;
import com.wowtown.wowtownbackend.chatroom.application.dto.request.CreateOrUpdateMultiChatRoomDto;
import com.wowtown.wowtownbackend.chatroom.application.dto.request.EnterOrExitMultiChatRoomDto;
import com.wowtown.wowtownbackend.chatroom.domain.MultiChatRoom;
import com.wowtown.wowtownbackend.chatroom.domain.MultiChatRoomRepository;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroup;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MultiChatRoomCommandExecutor {
    private final MultiChatRoomRepository multiChatRoomRepository;
    private final MultiChatRoomMapper multiChatRoomMapper;

    private final StudyGroupRepository studyGroupRepository;

   //Create와 Update는 방장만이, Delete는 cascade + 추가로 수시로 방장이 삭제할 수 있게

    @Transactional
    public long createMultiChatRoom(
            Long studyGroupId,
            String name,
            int personnel
            )
    {
        Optional<StudyGroup > findStudyGroup  = studyGroupRepository.findById(studyGroupId);
        MultiChatRoom multiChatRoom =
                multiChatRoomRepository.save(multiChatRoomMapper.toMultiChatRoom(name,personnel,findStudyGroup.get()));
        return multiChatRoom.getId();
    }
    @Transactional
    public boolean updateMultiChatRoom(
            CreateOrUpdateMultiChatRoomDto dto,
            long multiChatRoomId,
            Avatar avatar)
    {
        MultiChatRoom findMultiChatRoom =
                multiChatRoomRepository.findById(multiChatRoomId)
                        .orElseThrow(()->new IllegalArgumentException("존재하지 않는 공고 채팅방입니다."));
        StudyGroup findStudyGroup = findMultiChatRoom.getStudyGroup();
        if (findStudyGroup.checkAvatarStudyGroupRoleIsHost(avatar)){
            findMultiChatRoom.updateMultiChatRoom(multiChatRoomMapper.toUpdateMultiChatRoom(dto.getName(),dto.getPersonnel()));
            return true;
        }
        throw  new IllegalArgumentException("방장이 아니면 오픈채팅방을 업데이트할 수 없습니다");
    }
    @Transactional
    public boolean updateMultiChatRoomWithStudyGroupUpdate(
            Long studyGroupId,
            String name,
            int personnel
    ){
        Optional<StudyGroup > findStudyGroup  = studyGroupRepository.findById(studyGroupId);
        MultiChatRoom findMultiChatRoom = findStudyGroup.get().getMultiChatRoom();
        findMultiChatRoom.updateMultiChatRoom(multiChatRoomMapper.toUpdateMultiChatRoom(name,personnel));
        return true;
    }

    @Transactional
    public boolean enterMultiChatRoom(
            Long multiChatRoomId,
            EnterOrExitMultiChatRoomDto dto,
            Avatar avatar
    ){
        Optional<MultiChatRoom> findMultiChatRoom = multiChatRoomRepository.findById(multiChatRoomId);
        Optional<StudyGroup> selectStudyGroup = studyGroupRepository.findById(dto.getStudyGroupId());
        List<StudyGroup> studyGroupList = studyGroupRepository.findByAvatarId(avatar.getId());
        if (studyGroupList.contains(selectStudyGroup.get())) {
            findMultiChatRoom.get().enterMultiChatRoom();  //참여인원수 올리기
            return true;
        }
        throw new IllegalArgumentException("현재 참여하고 있는 스터디그룹의 오픈채팅방이 아닙니다");
    }

    @Transactional
    public boolean exitMultiChatRoom(
            Long multiChatRoomId,
            EnterOrExitMultiChatRoomDto dto,
            Avatar avatar
    ){
        Optional<MultiChatRoom> findMultiChatRoom = multiChatRoomRepository.findById(multiChatRoomId);
        Optional<StudyGroup> selectStudyGroup = studyGroupRepository.findById(dto.getStudyGroupId());
        List<StudyGroup> studyGroupList = studyGroupRepository.findByAvatarId(avatar.getId());
        if (studyGroupList.contains(selectStudyGroup.get())) {
            findMultiChatRoom.get().exitMultiChatRoom(); //참여인원수 내리기
            return true;
        }
        throw new IllegalArgumentException("현재 참여하고 있는 스터디그룹의 오픈채팅방이 아닙니다");
    }

//    @Transactional
//    public boolean deleteMultiChatRoom( Long multiChatRoomId,Avatar avatar){
//
//
//        MultiChatRoom findMultiChatRoom =
//                multiChatRoomRepository
//                        .findById(multiChatRoomId)
//                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공고 채팅방입니다"));
//        multiChatRoomRepository.delete(findMultiChatRoom);
//        return true;
//    }
//    StudyGroup서 cascade. 채팅방은 저절로 만들어지고 삭제하고 입장,퇴장만 있을뿐 굳이 생성,삭제 의미 필요?


}
