package com.example.demo.club.common;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
/**
 *  Redis 데이터베이스와 상호작용하는 데 사용되는 유틸리티를 제공
 */
@Component
@RequiredArgsConstructor
public class RedisUtil {
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, Object> redisBlackListTemplate;

    /**
     *  key, value, expire time(minutes)을 매개변수로 받아 Redis에 저장
     */
    public void setValues(String key, Object o, long minutes) {
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(o.getClass()));
        redisTemplate.opsForValue().set(key, o, minutes, TimeUnit.MINUTES);
    }
    /**
     *  key를 매개변수로 받아서 Redis에서 해당하는 값을 반환
     */
    public String getValues(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        return value != null ? value.toString() : null;
    }
    /**
     *  key를 매개변수로 받아서 Redis에서 해당하는 값을 삭제
     */
    public boolean deleteValues(String key) {
        return redisTemplate.delete(key);
    }

    /**
     *  key, value, expire time(minutes)을 매개변수로 받아 Redis BlackList에 저장
     */
    public void setBlackList(String key, Object o) {
        redisBlackListTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(o.getClass()));
        redisBlackListTemplate.opsForValue().set(key, o);
    }
    /**
     *  key를 매개변수로 받아 Redis BlackList에 해당하는 값이 존재하는지 확인
     */
    public boolean hasKeyBlackList(String key) {
        return redisBlackListTemplate.hasKey(key);
    }
    public String getBlackList(String key) {
        Object value =  redisBlackListTemplate.opsForValue().get(key);
        return value != null ? value.toString() : null;
    }
    /**
     *  key를 매개변수로 받아서 Redis BlackList에서 해당하는 값을 삭제
     */
    public boolean deleteBlackList(String key) {
        return redisBlackListTemplate.delete(key);
    }
}
