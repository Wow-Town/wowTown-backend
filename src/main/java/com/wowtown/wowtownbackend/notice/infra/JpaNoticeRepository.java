package com.wowtown.wowtownbackend.notice.infra;

import com.wowtown.wowtownbackend.common.domain.InterestType;
import com.wowtown.wowtownbackend.notice.domain.Notice;
import com.wowtown.wowtownbackend.notice.domain.NoticeRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface JpaNoticeRepository
    extends JpaRepository<Notice, Long>, com.wowtown.wowtownbackend.notice.domain.NoticeRepository {
  @Query(
      "select s from Notice as s where s.channel.id =:channelId and s.subject like %:subject%")
  List<Notice> findByChannelIdAndSubjectContaining(
      @Param("channelId") Long channelId, @Param("subject") String subject);

  @Query(
      "select distinct s from Notice as s join s.interestSet as i where s.channel.id =:channelId and i.type in :interestSet")
  List<Notice> findByChannelIdAndInterestList(
      @Param("channelId") Long channelId, @Param("interestSet") Set<InterestType> interestSet);

  @Query(
      "select distinct s from Notice as s join s.interestSet as i where s.channel.id =:channelId and s.subject like %:subject% and i.type in :interestSet")
  List<Notice> findByChannelIdAndSubjectContainingAndInterestList(
      @Param("channelId") Long channelId,
      @Param("subject") String subject,
      @Param("interestSet") Set<InterestType> interestSet);
}
