package com.wowtown.wowtownbackend.channel.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
public class Channel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String channelName;

  // @OneToMany(mappedBy = "channel")
  // private List<StudyGroup> studyGroups = new ArrayList<>();

  protected Channel() {}

  public Channel(String channelName) {
    this.channelName = channelName;
  }
}
