package com.wowtown.wowtownbackend.character.domain;

import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.common.domain.Interest;
import com.wowtown.wowtownbackend.studyGroup.domain.CharacterStudyGroup;
import com.wowtown.wowtownbackend.user.domain.User;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickName;

    private String description;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    @CollectionTable(name = "character_interest")
    private Set<Interest> interestList = new HashSet<>(); //생각해보니 db는 컬렉션이 존재하지 않아 테이블이 하나 더 필요할듯

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Channel_ID")
    private Channel channel;

    @OneToMany(mappedBy = "character",cascade = CascadeType.ALL)
    private List<CharacterChatRoom> characterChatRooms = new ArrayList<>();

    @OneToMany(mappedBy = "character",cascade = CascadeType.ALL)
    private List<CharacterStudyGroup> characterStudyGroups = new ArrayList<>();

    //set or list 둘중 선택
//    @OneToMany(mappedBy = "character",cascade = CascadeType.ALL)
//    private Set<Character> following = new HashSet<>();
//    @OneToMany(mappedBy = "character",cascade = CascadeType.ALL)
//    private Set<Character> follower = new HashSet<>();


    protected Character(){}

    public Character(String nickName, String description){
        this.nickName = nickName;
        this.description= description;
    }
}


