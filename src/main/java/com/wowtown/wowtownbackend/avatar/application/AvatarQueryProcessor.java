package com.wowtown.wowtownbackend.avatar.application;

import com.wowtown.wowtownbackend.avatar.application.common.AvatarMapper;
import com.wowtown.wowtownbackend.avatar.application.dto.response.GetAvatarDto;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.avatar.domain.AvatarChatRoom;
import com.wowtown.wowtownbackend.avatar.domain.AvatarChatRoomRepository;
import com.wowtown.wowtownbackend.avatar.domain.AvatarRepository;
import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
import com.wowtown.wowtownbackend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AvatarQueryProcessor {

  private final AvatarRepository avatarRepository;
  private final AvatarChatRoomRepository avatarChatRoomRepository;
  private final AvatarMapper avatarMapper;

  public GetAvatarDto getAvatar(Channel channel, User user) {
    Avatar findAvatar =
        avatarRepository
            .findAvatarWithChannelIdAndUserId(channel.getId(), user.getId())
            .orElseThrow(() -> new InstanceNotFoundException("아바타가 존재하지 않습니다."));
    return avatarMapper.toGetAvatarDto(findAvatar);
  }

  public List<Avatar> getAvatarWithChatRoomUUID(UUID chatRoomUUID) {
    return avatarRepository.findAvatarByChatRoomUuid(chatRoomUUID);
  }

  public List<AvatarChatRoom> getAvatarChatRoomWithAvatar(Avatar avatar) {
    return avatarChatRoomRepository.findChatRoomAvatarByAvatarId(avatar.getId());
  }
  ///친구 관련
  public Avatar getAvatarWithAvatarId(long avatarId){
    Avatar findAvatar = avatarRepository
            .findById(avatarId)
            .orElseThrow(()->new InstanceNotFoundException("없는 아바타입니다"));
    return findAvatar;
  }
  public List<GetAvatarDto> getFriendAvatarDto(List<Long> avatarIdList){
    List<Avatar> avatarList = new ArrayList<>();
    List<GetAvatarDto> avatarDtoList = new ArrayList<>();
    for(Long avatarId : avatarIdList){
      Optional <Avatar> findAvatar = avatarRepository.findById(avatarId);
      if(findAvatar.isPresent()) {
        avatarList.add(findAvatar.get());
      }
    }
    for(Avatar avatar : avatarList){
      avatarDtoList.add(avatarMapper.toGetAvatarDto(avatar));
    }
    return avatarDtoList;
  }




}
