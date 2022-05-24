package com.wowtown.wowtownbackend.channel.domain;


import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroup;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String channelName;

    /*@OneToMany(mappedBy = "channel")
    private List<StudyGroup> studyGroups = new ArrayList<>();*/


    protected Channel(){}

    public Channel(String channelName){
        this.channelName= channelName;
    }
}
