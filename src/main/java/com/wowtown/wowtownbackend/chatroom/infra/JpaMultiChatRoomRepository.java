package com.wowtown.wowtownbackend.chatroom.infra;

import com.wowtown.wowtownbackend.chatroom.domain.MultiChatRoom;
import com.wowtown.wowtownbackend.chatroom.domain.MultiChatRoomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface JpaMultiChatRoomRepository
        extends JpaRepository<MultiChatRoom,Long> , MultiChatRoomRepository {

}
