package com.wowtown.wowtownbackend.channel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wowtown.wowtownbackend.channel.application.ChannelQueryProcessor;
import com.wowtown.wowtownbackend.channel.domain.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ChannelQueryController.class)
public class ChannelQueryControllerTest {
  @Autowired MockMvc mvc;

  @Autowired ObjectMapper mapper;

  @MockBean ChannelQueryProcessor channelQueryProcessor;

  @MockBean ChannelRepository channelRepository;

  //  @Test
  //  void getAllChannel() throws Exception {
  //    // given
  //    GetChannelDto getChannelDto1 = new GetChannelDto("channel1", 100, 0);
  //    GetChannelDto getChannelDto2 = new GetChannelDto("channel2", 200, 1);
  //    List<GetChannelDto> getChannelDtoList = new ArrayList<>();
  //    getChannelDtoList.add(getChannelDto1);
  //    getChannelDtoList.add(getChannelDto2);
  //
  //    // when
  //    doReturn(getChannelDtoList).when(channelQueryProcessor).getAllChannelList();
  //
  //    MockHttpServletRequestBuilder builder =
  //        MockMvcRequestBuilders.get("/channels")
  //            .contentType(MediaType.APPLICATION_JSON_VALUE)
  //            .accept(MediaType.APPLICATION_JSON)
  //            .characterEncoding("UTF-8");
  //    // then
  //    mvc.perform(builder)
  //        .andDo(print())
  //        .andExpect(status().isOk())
  //        .andExpect(jsonPath("$[*].channelName", containsInAnyOrder("channel1", "channel2")))
  //        .andExpect(jsonPath("$[*].maxJoinNum", containsInAnyOrder(100, 200)))
  //        .andExpect(jsonPath("$[*].currentJoinNum", containsInAnyOrder(0, 1)));
  //  }
}
