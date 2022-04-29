package com.wowtown.wowtownbackend.user.domain;

import com.wowtown.wowtownbackend.channel.domain.Channel;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class UserChannel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    public UserChannel(){}
}
