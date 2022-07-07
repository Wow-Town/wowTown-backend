// package com.wowtown.wowtownbackend.studyGroup.domain;
//
//// import com.wowtown.wowtownbackend.common.domain.Interest;
//
// import com.wowtown.wowtownbackend.common.domain.InterestType;
// import lombok.ToString;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;
// import java.util.Set;
//
// import static org.assertj.core.api.Assertions.assertThat;
//
// @ToString
// @DataJpaTest
// public class StudyGroupRepositoryTest {
//  @Autowired private StudyGroupRepository studyGroupRepository;
//
//  @Test
//  void save() {
//    StudyGroup studyGroup = new StudyGroup("wowTown", 10, "알고리즘 스터디", StudyGroupStatus.OPEN);
//    studyGroupRepository.save(studyGroup);
//    StudyGroup result = studyGroupRepository.findById(studyGroup.getId()).get();
//    assertThat(result).isEqualTo(studyGroup);
//  }
//
//  @Test
//  public void delete() {
//    StudyGroup studyGroup = new StudyGroup("wowTown", 10, "알고리즘 스터디", StudyGroupStatus.OPEN);
//    studyGroupRepository.save(studyGroup);
//    Long tmp = studyGroup.getId();
//    Optional<StudyGroup> studyGroup1 = studyGroupRepository.findById(tmp);
//    org.junit.jupiter.api.Assertions.assertTrue(studyGroup1.isPresent());
//    studyGroupRepository.delete(studyGroup);
//    studyGroup1 = studyGroupRepository.findById(tmp);
//    org.junit.jupiter.api.Assertions.assertFalse(studyGroup1.isPresent());
//  }
//
//  @Test
//  public void findById() {
//    StudyGroup studyGroup = new StudyGroup("wowTown", 10, "알고리즘 스터디", StudyGroupStatus.OPEN);
//    studyGroupRepository.save(studyGroup);
//    StudyGroup result = studyGroupRepository.findById(studyGroup.getId()).get();
//    assertThat(result).isEqualTo(studyGroup);
//  }
//
//  @Test
//  public void findByName() {
//    StudyGroup studyGroup = new StudyGroup("wowTown", 10, "알고리즘 스터디", StudyGroupStatus.OPEN);
//    studyGroupRepository.save(studyGroup);
//    StudyGroup studyGroup1 = new StudyGroup("wowTown2", 20, "리액트 스터디", StudyGroupStatus.OPEN);
//    studyGroupRepository.save(studyGroup1);
//
//    List<StudyGroup> studyGroupList = studyGroupRepository.findBySubjectContaining("wTo");
//    assertThat(studyGroupList.size()).isEqualTo(2);
//  }
//
//  @Test
//  public void findByInterestType() {
//    StudyGroup studyGroup = new StudyGroup("wowTown", 10, "알고리즘 스터디", StudyGroupStatus.OPEN);
//    studyGroup.getInterestTypes().add(InterestType.BACKEND);
//    studyGroup.getInterestTypes().add(InterestType.FRONTEND);
//    studyGroupRepository.save(studyGroup);
//    StudyGroup studyGroup1 = new StudyGroup("wowTown2", 20, "알고리즘 스터디2", StudyGroupStatus.OPEN);
//    studyGroup1.getInterestTypes().add(InterestType.BACKEND);
//    studyGroupRepository.save(studyGroup1);
//    StudyGroup studyGroup2 = new StudyGroup("wowTown3", 30, "알고리즘 스터디3", StudyGroupStatus.OPEN);
//    studyGroup2.getInterestTypes().add(InterestType.BACKEND);
//    studyGroup2.getInterestTypes().add(InterestType.CPP);
//    studyGroupRepository.save(studyGroup2);
//    StudyGroup studyGroup3 = new StudyGroup("wowTown4", 40, "알고리즘 스터디4", StudyGroupStatus.OPEN);
//    studyGroupRepository.save(studyGroup3);
//    Set<StudyGroup> studyGroupList =
// studyGroupRepository.findByInterestTypes(InterestType.BACKEND);
//    for (StudyGroup sg : studyGroupList) {
//      System.out.println(sg.getSubject());
//    }
//    assertThat(studyGroupList.size()).isEqualTo(3);
//  }
//
//  /* @Test
//  public void findByManyInterestTypes() { //한개만 포함된 거까지 다 나오는
//    StudyGroup studyGroup = new StudyGroup();
//    studyGroup.setStudyGroupName("wowTown");
//    studyGroup.getInterestTypes().add(InterestType.BACKEND);
//    studyGroup.getInterestTypes().add(InterestType.FRONTEND);
//    studyGroup.getInterestTypes().add(InterestType.CODINGTEST);
//    studyGroupRepository.save(studyGroup);
//
//    StudyGroup studyGroup1 = new StudyGroup();
//    studyGroup1.setStudyGroupName("wowTown2");
//    studyGroup1.getInterestTypes().add(InterestType.BACKEND);
//    studyGroup1.getInterestTypes().add(InterestType.CODINGTEST);
//    studyGroupRepository.save(studyGroup1);
//
//    StudyGroup studyGroup2 = new StudyGroup();
//    studyGroup2.setStudyGroupName("wowTown3");
//    studyGroup2.getInterestTypes().add(InterestType.BACKEND);
//    studyGroupRepository.save(studyGroup2);
//
//
//    List<InterestType> interestTypes = new ArrayList<>();
//    interestTypes.add(InterestType.CODINGTEST);
//    interestTypes.add(InterestType.BACKEND);
//    Set<StudyGroup> studyGroupList = studyGroupRepository.findByManyInterestTypes(interestTypes);
//    for(StudyGroup sg:studyGroupList){
//      System.out.println(sg.getStudyGroupName());
//    }
//    assertThat(studyGroupList.size()).isEqualTo(2);
//  }*/
//  @Test
//  public void findByManyInterestTypes2() { // not in 으로 편법 느낌
//    StudyGroup studyGroup = new StudyGroup("wowTown", 10, "알고리즘 스터디", StudyGroupStatus.OPEN);
//    studyGroup.getInterestTypes().add(InterestType.BACKEND);
//    studyGroup.getInterestTypes().add(InterestType.FRONTEND);
//    studyGroup.getInterestTypes().add(InterestType.CODINGTEST);
//    studyGroupRepository.save(studyGroup);
//
//    StudyGroup studyGroup1 = new StudyGroup("wowTown2", 20, "알고리즘 스터디2", StudyGroupStatus.OPEN);
//    studyGroup1.getInterestTypes().add(InterestType.BACKEND);
//    studyGroup1.getInterestTypes().add(InterestType.CODINGTEST);
//    studyGroupRepository.save(studyGroup1);
//
//    StudyGroup studyGroup2 = new StudyGroup("wowTown3", 30, "알고리즘 스터디3", StudyGroupStatus.OPEN);
//    studyGroup2.getInterestTypes().add(InterestType.BACKEND);
//    studyGroupRepository.save(studyGroup2);
//
//    List<InterestType> interestTypes = new ArrayList<>();
//    interestTypes.add(InterestType.CODINGTEST);
//    interestTypes.add(InterestType.BACKEND);
//    Set<StudyGroup> studyGroupList = studyGroupRepository.findByManyInterestTypes2(interestTypes);
//    for (StudyGroup sg : studyGroupList) {
//      System.out.println(sg.getSubject());
//      for (InterestType interestType : sg.interestTypes) {
//        System.out.println(interestType);
//      }
//    }
//    assertThat(studyGroupList.size()).isEqualTo(1);
//  }
//
//  @AfterEach
//  public void afterEach() {
//    studyGroupRepository.deleteAll();
//  }
// }
