package com.numble.webnovelservice.util.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate redisTemplate;

    public void setValues(String title, String type, Duration duration){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(title, type, duration);
    }

    public String getValues(String title){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(title);
    }

    public void delValues(String title){
        redisTemplate.delete(title);
    }
}
