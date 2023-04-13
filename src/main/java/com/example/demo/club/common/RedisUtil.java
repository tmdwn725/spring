package com.example.demo.club.common;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    private final RedisTemplate<String, String> redisTemplate;
    private final RedisTemplate<String, Object> redisBlackListTemplate;

    public RedisUtil(RedisTemplate<String, String> redisTemplate, RedisTemplate<String, Object> redisBlackListTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisBlackListTemplate = redisBlackListTemplate;
    }

    public void setValues(String key, String data) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, data);
    }

    public void setValues(String key, String data, Duration duration) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, data, duration);
    }

    public String getValues(String key) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(key);
    }

    public void setBlackList(String key, Object o, int minutes) {
        redisBlackListTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(o.getClass()));
        redisBlackListTemplate.opsForValue().set(key, o, minutes, TimeUnit.MINUTES);
    }

    public Object getBlackList(String key) {
        return redisBlackListTemplate.opsForValue().get(key);
    }

    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }
}
