package com.wowtown.wowtownbackend.studyGroup.application;


import com.wowtown.wowtownbackend.chatroom.application.MultiChatRoomCommandExecutor;
import com.wowtown.wowtownbackend.chatroom.application.dto.request.CreateMultiChatRoomDto;
import com.wowtown.wowtownbackend.chatroom.domain.MultiChatRoom;
import com.wowtown.wowtownbackend.chatroom.domain.MultiChatRoomRepository;
import com.wowtown.wowtownbackend.studyGroup.application.common.StudyGroupMapper;
import com.wowtown.wowtownbackend.studyGroup.application.dto.request.CreateStudyGroupDto;
import com.wowtown.wowtownbackend.studyGroup.application.dto.request.GetStudyGroupDtoReq;
import com.wowtown.wowtownbackend.studyGroup.application.dto.response.GetStudyGroupDtoRes;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroup;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroupRepository;
import com.wowtown.wowtownbackend.user.application.dto.response.GetUserChannelDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyGroupCommandExecutor {
    private final StudyGroupRepository studyGroupRepository;
    private final StudyGroupMapper studyGroupMapper;
    private final MultiChatRoomCommandExecutor multiChatRoomCommandExecutor;


    @Transactional
    public long createStudyGroup(CreateStudyGroupDto dto){ //공고등록
        /*studyGroupRepository.findByName(studyGroup.getStudyGroupName()) //같은 제목 검증 (같은 제목도 허용할지?)
                .ifPresent(m ->{
            throw new IllegalStateException("이미 존재하는 스터디 그룹 제목입니다");
        });*/
        CreateMultiChatRoomDto multiChatRoomDto = new CreateMultiChatRoomDto(dto.getStudyGroupName(), dto.getPersonnel());
        StudyGroup studyGroup =
                studyGroupRepository.save(studyGroupMapper.toStudyGroup(dto.getStudyGroupName(),dto.getPersonnel(), dto.getStudyDetail()));

        multiChatRoomCommandExecutor.createMultiChatRoom(studyGroup.getId(),multiChatRoomDto);

        return studyGroup.getId();
    }

    //현재 완전히 구현하지 못 함(방장만이 삭제,수정 가능)
    public boolean updateStudyGroup(long studyGroupId, GetStudyGroupDtoReq dto){
        StudyGroup findStudyGroup =
                studyGroupRepository
                        .findById(studyGroupId)
                        .orElseThrow(() ->new IllegalArgumentException("존재하지 않는 스터디 그룹입니다"));
        findStudyGroup.updateStudyGroup(studyGroupMapper.toStudyGroup(dto.getStudyGroupName(),dto.getPersonnel(),dto.getStudyDetail(),dto.getIsOpen()));
        return true;
    }
    @Transactional
    public boolean deleteStudyGroup(long studyGroupId){
        StudyGroup findStudyGroup =
                studyGroupRepository
                .findById(studyGroupId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디 그룹입니다"));


        studyGroupRepository.delete(findStudyGroup);
        return true;
    }






}
