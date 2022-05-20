package com.wowtown.wowtownbackend.channel.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
public class Channel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 언리얼 매치메이커에서 채널 이름만 있으면 세션에 연결 해준다.
  private String channelName;

  private int maxJoinNum;

  private int currentJoinNum;

  private LocalDateTime createAt;

  private LocalDateTime updateAt;

  // @OneToMany(mappedBy = "channel")
  // private List<StudyGroup> studyGroups = new ArrayList<>();

  protected Channel() {}

  public Channel(String channelName, int maxJoinNum) {
    this.channelName = channelName;
    this.maxJoinNum = maxJoinNum;
    this.currentJoinNum = 0;
    this.createAt = LocalDateTime.now();
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Channel)) {
      return false;
    }
    Channel channel = (Channel) o;
    return this.id == channel.id && this.channelName == channel.channelName;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.channelName);
  }
}
