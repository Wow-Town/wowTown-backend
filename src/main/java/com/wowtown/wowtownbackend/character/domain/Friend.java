package com.wowtown.wowtownbackend.character.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHARACTER_ID")
    private Character following;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHARACTER_ID")
    private Character follower;

    private LocalDateTime createTime;

    public Friend(){}
}
