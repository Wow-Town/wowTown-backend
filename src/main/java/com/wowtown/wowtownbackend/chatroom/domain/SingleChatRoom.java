/*
package com.wowtown.wowtownbackend.chatroom.domain;


import com.wowtown.wowtownbackend.character.domain.CharacterChatRoom;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class SingleChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomName;

    @OneToMany(mappedBy = "singleChatRoom",cascade = CascadeType.ALL)
    private List<CharacterChatRoom> characterChatRooms = new ArrayList<>();

    protected SingleChatRoom(){}

    public SingleChatRoom(String roomName){
        this.roomName= roomName;
    }
}
*/
