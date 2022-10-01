// package com.wowtown.wowtownbackend.avatar.domain;
//
// import org.assertj.core.api.Assertions;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.transaction.annotation.Transactional;
//
// import java.util.Optional;
//
// import static org.junit.jupiter.api.Assertions.*;
//
// @DataJpaTest
// class AvatarRepositoryTest {
//
//    @Autowired private AvatarRepository avatarRepository;
//    Avatar avatarOrg;
//
//    @BeforeEach
//    @Transactional
//    void init(){
//        avatarOrg = new Avatar("starwook","hey");
//        avatarRepository.save(avatarOrg);
//
//    }
//    @Test
//    void findAvatar(){
//        long avatarId = 1;
//        Optional<Avatar> avatar = avatarRepository.findById(avatarId);
//        Assertions.assertThat(avatar.get()).isEqualTo(avatarOrg);
//    }
//
// }
