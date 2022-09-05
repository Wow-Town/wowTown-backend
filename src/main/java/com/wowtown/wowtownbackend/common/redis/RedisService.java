package com.wowtown.wowtownbackend.common.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {

  private final StringRedisTemplate redisTemplate;

  // 키-벨류 설정
  public void setValues(String token, String email, Duration duration) {
    ValueOperations<String, String> values = redisTemplate.opsForValue();
    values.set(token, email, duration);
  }

  // 키값으로 벨류 가져오기
  public String getValues(String token) {
    ValueOperations<String, String> values = redisTemplate.opsForValue();
    return values.get(token);
  }

  // 키-벨류 삭제
  public void delValues(String token) {
    redisTemplate.delete(token.substring(7));
  }
}
