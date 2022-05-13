package com.wowtown.wowtownbackend.channel.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Getter
@Entity
public class Channel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 언리얼 매치메이커에서 채널 이름만 있으면 세션에 연결 해준다.
  private String channelName;

  private int joinNum;

  // @OneToMany(mappedBy = "channel")
  // private List<StudyGroup> studyGroups = new ArrayList<>();

  protected Channel() {}

  public Channel(String channelName) {
    this.channelName = channelName;
    this.joinNum = 0;
  }

  public void joinChannel() {
    this.joinNum++;
  }

  public void leaveChannel() {
    this.joinNum--;
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
