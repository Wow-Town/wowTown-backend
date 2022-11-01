package com.wowtown.wowtownbackend.privateSpace.application;

import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.privateSpace.application.common.PrivateSpaceMapper;
import com.wowtown.wowtownbackend.privateSpace.application.dto.response.GetPrivateSpaceDto;
import com.wowtown.wowtownbackend.privateSpace.domain.AvatarPrivateSpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrivateSpaceQueryProcessor {
  private final AvatarPrivateSpaceRepository avatarPrivateSpaceRepository;
  private final PrivateSpaceMapper privateSpaceMapper;

  public List<GetPrivateSpaceDto> getPrivateSpaceListByAvatar(Avatar avatar) {
    List<GetPrivateSpaceDto> findAvatarChatRooms =
        avatarPrivateSpaceRepository.findAvatarPrivateSpaceByAvatarId(avatar.getId()).stream()
            .map(
                avatarPrivateSpace ->
                    privateSpaceMapper.toGetPrivateSpaceDto(
                        avatarPrivateSpace.getPrivateSpace().getNotice().getChatRoom().getUuid(),
                        avatarPrivateSpace.getPrivateSpace().getUuid(),
                        avatarPrivateSpace.getRoomName(),
                        avatarPrivateSpace.getPrivateSpace().getParticipantsNum()))
            .collect(Collectors.toList());

    return findAvatarChatRooms;
  }
}
