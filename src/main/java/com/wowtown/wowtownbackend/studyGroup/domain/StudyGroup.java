package com.wowtown.wowtownbackend.studyGroup.domain;


import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.chatroom.domain.MultiChatRoom;
import com.wowtown.wowtownbackend.common.domain.InterestType;
import com.wowtown.wowtownbackend.privateSpace.domain.PrivateSpace;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class StudyGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studyGroupName; //제목
    private Integer personnel; //구하는 인원수
    private String studyDetail;
    private int isOpen;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    List<InterestType> interestTypes = new ArrayList<>();

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name ="CHANNEL_ID")
//    private Channel channel;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "PRIVATE_SPACE_ID")
//    private PrivateSpace privateSpace;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "MULTI_CHAT_ROOM_ID")
//    private MultiChatRoom openChatRoom;
//
//    @OneToMany(mappedBy = "studyGroup",cascade = CascadeType.ALL)
//    private List<CharacterStudyGroup> characterStudyGroups = new ArrayList<>();

    protected StudyGroup(){}

    public StudyGroup(String studyGroupName, int personnel, String studyDetail, int isOpen){
        this.studyGroupName=studyGroupName;
        this.personnel= personnel;
        this.studyDetail= studyDetail;
        this.isOpen= isOpen;
    }
    public void updateStudyGroup(StudyGroup studyGroup){
        this.studyGroupName=studyGroup.studyGroupName;
    this.personnel = studyGroup.personnel;
        this.studyDetail= studyGroup.studyDetail;
        this.isOpen= studyGroup.isOpen;
    }
}
