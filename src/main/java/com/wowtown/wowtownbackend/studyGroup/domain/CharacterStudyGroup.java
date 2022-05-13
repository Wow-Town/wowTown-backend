// package com.wowtown.wowtownbackend.studyGroup.domain;
//
// import com.wowtown.wowtownbackend.character.domain.Character;
// import lombok.Getter;
//
// import javax.persistence.*;
//
// @Getter
// @Entity
// public class CharacterStudyGroup {
//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  Long id;
//
//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "character_id")
//  private Character character;
//
//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "study_group_id")
//  private StudyGroup studyGroup;
// }
