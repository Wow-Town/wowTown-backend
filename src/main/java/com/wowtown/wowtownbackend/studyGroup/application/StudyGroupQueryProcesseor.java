package com.wowtown.wowtownbackend.studyGroup.application;

import com.wowtown.wowtownbackend.studyGroup.application.common.StudyGroupMapper;
import com.wowtown.wowtownbackend.studyGroup.application.dto.request.GetStudyGroupByNameDto;
import com.wowtown.wowtownbackend.studyGroup.application.dto.response.GetStudyGroupDtoRes;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyGroupQueryProcesseor {
    private final StudyGroupRepository studyGroupRepository;
    private final StudyGroupMapper studyGroupMapper;


    public List<GetStudyGroupDtoRes> getAllStudyGroup(){
        List<GetStudyGroupDtoRes> studyGroupDtoList = studyGroupRepository
                .findAll()
                .stream()
                .filter(studyGroup->studyGroup.getIsOpen() ==1) //열려있는 것만?
                .map(studyGroup -> studyGroupMapper.toGetStudyGroupDtoRes(studyGroup))
                .collect(Collectors.toList());
        return studyGroupDtoList;
    }

    public List<GetStudyGroupDtoRes> getStudyGroupByName(GetStudyGroupByNameDto dto){
        List<GetStudyGroupDtoRes> studyGroupDtoList = studyGroupRepository
                .findByStudyGroupNameContaining(dto.getStudyGroupNamePart())
                .stream()
                .filter(studyGroup->studyGroup.getIsOpen() ==1)
                .map(studyGroup -> studyGroupMapper.toGetStudyGroupDtoRes(studyGroup))
                .collect(Collectors.toList());
        return studyGroupDtoList;
    }

//    public List<GetStudyGroupDtoRes> getStudyGroupByInterest(){
//        List<GetStudyGroupDtoRes> studyGroupDtoList = studyGroupRepository
//                .findByInterestTypes()
//                .stream()
//                .filter(studyGroup->studyGroup.getIsOpen() ==1)
//                .map(studyGroup -> studyGroupMapper.toGetStudyGroupDtoRes(studyGroup))
//                .collect(Collectors.toList());
//        return studyGroupDtoList;
//
//    }
}
