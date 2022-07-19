package com.wowtown.wowtownbackend.chatroom.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetMultiChatRoomDto {
    private Long multiChatRoomId;
    private String name;
    private Integer personnel;
    private Integer participantsNum;
}
