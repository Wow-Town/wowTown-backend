package com.wowtown.wowtownbackend.avatar.domain;

import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.common.domain.Interest;
import com.wowtown.wowtownbackend.user.domain.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
public class Avatar {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nickName;

  private String description;

  private LocalDateTime createAt;

  private LocalDateTime updateAt;

  @Enumerated(EnumType.STRING)
  @ElementCollection
  @CollectionTable(name = "avatar_interest")
  private Set<Interest> interestList = new HashSet<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_ID")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "Channel_ID")
  private Channel channel;

  //  @OneToMany(mappedBy = "character", cascade = CascadeType.ALL)
  //  private List<CharacterChatRoom> characterChatRooms = new ArrayList<>();

  //  @OneToMany(mappedBy = "character", cascade = CascadeType.ALL)
  //  private List<CharacterStudyGroup> characterStudyGroups = new ArrayList<>();

  //  // set or list 둘중 선택
  //  @OneToMany(mappedBy = "character", cascade = CascadeType.ALL)
  //  private Set<Character> following = new HashSet<>();
  //
  //  @OneToMany(mappedBy = "character", cascade = CascadeType.ALL)
  //  private Set<Character> follower = new HashSet<>();

  protected Avatar() {}

  public Avatar(String nickName, String description) {
    this.nickName = nickName;
    this.description = description;
    this.createAt = LocalDateTime.now();
    this.updateAt = null;
  }

  public Avatar(String nickName, String description, User user, Channel channel) {
    this.nickName = nickName;
    this.description = description;
    this.user = user;
    this.channel = channel;
    this.createAt = LocalDateTime.now();
    this.updateAt = null;
  }

  public void addInterest(Interest payload) {
    this.interestList.add(payload);
  }

  public void updateCharacter(Avatar updatePayload) {
    this.nickName = updatePayload.getNickName();
    this.description = updatePayload.getDescription();
    this.interestList = updatePayload.getInterestList();
    this.updateAt = LocalDateTime.now();
  }
}