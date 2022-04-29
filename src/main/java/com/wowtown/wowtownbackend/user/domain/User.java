package com.wowtown.wowtownbackend.user.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<UserChannel> userChannelList = new ArrayList<>();

    protected User(){}

    private User(String email, String password){
        this.email= email;
        this.password = password;//sha256 encrypt된 pw들어옴
    }

    //개인정보 수집 동의
}
