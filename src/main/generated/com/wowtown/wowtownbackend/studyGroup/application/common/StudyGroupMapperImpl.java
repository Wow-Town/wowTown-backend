package com.wowtown.wowtownbackend.studyGroup.application.common;

import com.wowtown.wowtownbackend.studyGroup.application.dto.response.GetStudyGroupDtoRes;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroup;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-24T17:47:23+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 14.0.2 (Oracle Corporation)"
)
@Component
public class StudyGroupMapperImpl implements StudyGroupMapper {

    @Override
    public GetStudyGroupDtoRes toGetStudyGroupDtoRes(StudyGroup studyGroup) {
        if ( studyGroup == null ) {
            return null;
        }

        GetStudyGroupDtoRes getStudyGroupDtoRes = new GetStudyGroupDtoRes();

        getStudyGroupDtoRes.setStudyGroupId( studyGroup.getId() );
        getStudyGroupDtoRes.setStudyGroupName( studyGroup.getStudyGroupName() );
        getStudyGroupDtoRes.setPersonnel( studyGroup.getPersonnel() );
        getStudyGroupDtoRes.setStudyDetail( studyGroup.getStudyDetail() );
        getStudyGroupDtoRes.setIsOpen( studyGroup.getIsOpen() );

        return getStudyGroupDtoRes;
    }
}
