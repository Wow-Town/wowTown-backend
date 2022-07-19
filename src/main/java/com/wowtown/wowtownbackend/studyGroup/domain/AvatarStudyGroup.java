 package com.wowtown.wowtownbackend.studyGroup.domain;


 import com.wowtown.wowtownbackend.avatar.domain.Avatar;
 import lombok.Getter;

 import javax.persistence.*;

 @Getter
 @Entity
 public class AvatarStudyGroup {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "character_id")
  private Avatar avatar;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "study_group_id")
  private StudyGroup studyGroup;


   private int isHost;
 }
