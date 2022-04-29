package com.wowtown.wowtownbackend.privateSpace.domain;


import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroup;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class PrivateSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy= "privateSpace")
    private StudyGroup studyGroup;

    public PrivateSpace(){}
}
