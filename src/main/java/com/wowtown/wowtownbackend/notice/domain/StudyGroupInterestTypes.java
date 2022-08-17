/*
package com.wowtown.wowtownbackend.studyGroup.domain;

import com.wowtown.wowtownbackend.common.domain.InterestType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class StudyGroupInterestTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    InterestType interestType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="STUDY_GROUP_ID")
    private Notice studyGroup;
}
*/
