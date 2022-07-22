package com.wowtown.wowtownbackend.chatroom.infra;

import com.wowtown.wowtownbackend.chatroom.domain.MultiChatRoom;
import com.wowtown.wowtownbackend.chatroom.domain.MultiChatRoomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface JpaMultiChatRoomRepository
        extends JpaRepository<MultiChatRoom,Long> , MultiChatRoomRepository {

    @Query("select m from MultiChatRoom m ") //채널과 공고를 연결후 해야할
    List<MultiChatRoom> findByChannelIdAndAvatarId(
            @Param("channelId") Long channelId,
            @Param("avatarId") Long avatarId
    );

}