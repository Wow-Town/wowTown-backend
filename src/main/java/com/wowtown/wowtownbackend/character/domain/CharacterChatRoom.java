package com.wowtown.wowtownbackend.character.domain;


/*import com.wowtown.wowtownbackend.chatroom.domain.SingleChatRoom;*/
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class CharacterChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHARACTER_ID")
    private Character character;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHATROOM_ID")
    private SingleChatRoom singleChatRoom;*/

    public CharacterChatRoom(){}
}
