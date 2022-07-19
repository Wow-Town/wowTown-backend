package com.wowtown.wowtownbackend.chatroom.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMultiChatRoomDto {
    private String name;
    private Integer personnel;
}
