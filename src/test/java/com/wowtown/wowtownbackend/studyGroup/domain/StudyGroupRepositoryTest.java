package com.wowtown.wowtownbackend.studyGroup.domain;


//import com.wowtown.wowtownbackend.common.domain.Interest;
import com.wowtown.wowtownbackend.common.domain.InterestType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class StudyGroupRepositoryTest {
  @Autowired private StudyGroupRepository studyGroupRepository;


  @Test
  void save(){
      StudyGroup studyGroup = new StudyGroup();
      studyGroup.setStudyGroupName("wowTown");
      studyGroupRepository.save(studyGroup);
      StudyGroup result = studyGroupRepository.findById(studyGroup.getId()).get();
      assertThat(result).isEqualTo(studyGroup);
  }
  @Test
  public void delete(){
      StudyGroup studyGroup = new StudyGroup();
      studyGroup.setStudyGroupName("wowTown");
      studyGroupRepository.save(studyGroup);
      Long tmp = studyGroup.getId();
      Optional<StudyGroup> studyGroup1 = studyGroupRepository.findById(tmp);
      org.junit.jupiter.api.Assertions.assertTrue(studyGroup1.isPresent());
      studyGroupRepository.delete(studyGroup);
    studyGroup1 = studyGroupRepository.findById(tmp);
      org.junit.jupiter.api.Assertions.assertFalse(studyGroup1.isPresent());


  }
  @Test
  public void findById(){
      StudyGroup studyGroup = new StudyGroup();
      studyGroup.setStudyGroupName("wowTown");
      studyGroupRepository.save(studyGroup);
      StudyGroup result = studyGroupRepository.findById(studyGroup.getId()).get();
      assertThat(result).isEqualTo(studyGroup);
  }

  @Test
  public void findByName(){
      StudyGroup studyGroup = new StudyGroup();
      studyGroup.setStudyGroupName("wowTown");
      studyGroupRepository.save(studyGroup);
      StudyGroup studyGroup1 = new StudyGroup();
      studyGroup1.setStudyGroupName("wowTown2");
      studyGroupRepository.save(studyGroup1);

      List<StudyGroup> studyGroupList = studyGroupRepository.findByStudyGroupNameContaining("wTo");
      assertThat(studyGroupList.size()).isEqualTo(2);
  }
  @Test
  public void findByInterestType(){
      StudyGroup studyGroup = new StudyGroup();
      studyGroup.setStudyGroupName("wowTown");
      studyGroup.getInterestTypes().add(InterestType.BACKEND);
      studyGroup.getInterestTypes().add(InterestType.FRONTEND);
      studyGroupRepository.save(studyGroup);
      StudyGroup studyGroup1 = new StudyGroup();
      studyGroup1.setStudyGroupName("wowTown2");
     studyGroup1.getInterestTypes().add(InterestType.BACKEND);
      studyGroupRepository.save(studyGroup1);
      List<StudyGroup> studyGroupList =studyGroupRepository.findByInterestTypes(InterestType.BACKEND);
      assertThat(studyGroupList.size()).isEqualTo(2);

  }
  @Test
    public void findByManyInterestType(){
      studyGroupRepository.deleteAll();
        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setStudyGroupName("wowTown");
        studyGroup.getInterestTypes().add(InterestType.BACKEND);
        studyGroup.getInterestTypes().add(InterestType.FRONTEND);
        studyGroupRepository.save(studyGroup);

        StudyGroup studyGroup1 = new StudyGroup();
        studyGroup1.setStudyGroupName("wowTown2");
        studyGroup1.getInterestTypes().add(InterestType.BACKEND);
        studyGroupRepository.save(studyGroup1);
        List<StudyGroup> studyGroupList =new ArrayList<>();

        List<InterestType> interestTypes = new ArrayList<>();
        interestTypes.add(InterestType.BACKEND);
        interestTypes.add(InterestType.FRONTEND);

        studyGroupList =studyGroupRepository.findByManyInterestTypes(interestTypes);
        assertThat(studyGroupList.size()).isEqualTo(1);

    }

  @AfterEach
    public void afterEach(){
      studyGroupRepository.deleteAll();
  }
}