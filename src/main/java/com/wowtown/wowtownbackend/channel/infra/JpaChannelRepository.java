package com.wowtown.wowtownbackend.channel.infra;

import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.channel.domain.ChannelRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaChannelRepository extends JpaRepository<Channel, Long>, ChannelRepository {}
