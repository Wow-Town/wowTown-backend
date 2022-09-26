package com.wowtown.wowtownbackend.chatroom.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UploadDto {

  private UUID chatRoomUUID;

  private MultipartFile[] files;
}
