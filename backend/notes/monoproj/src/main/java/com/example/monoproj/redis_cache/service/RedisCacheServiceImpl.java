package com.example.monoproj.redis_cache.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisCacheServiceImpl implements RedisCacheService {

    final private StringRedisTemplate redisTemplate;

    @Override
    public <K, V> void setKeyAndValue(K key, V value) {
        setKeyAndValue(key, value, Duration.ofMinutes(720));  // 기본 TTL 720분 적용
    }

    @Override
    public <K, V> void setKeyAndValue(K key, V value, Duration ttl) {
        String keyAsString = String.valueOf(key);
        String valueAsString = String.valueOf(value);

        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        valueOps.set(keyAsString, valueAsString, ttl);
    }

//    @Override
//    public Long getValueByKey(String token) {
//        ValueOperations<String, String> value = redisTemplate.opsForValue();
//        String tmpAccountId = value.get(token);
//        Long accountId;
//
//        if (tmpAccountId == null) {
//            accountId = null;
//        } else {
//            accountId = Long.parseLong(tmpAccountId);
//        }
//
//        return accountId;
//    }

    @Override
    public <T> T getValueByKey(String key, Class<T> clazz) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String value = ops.get(key);
        if (value == null) {
            return null;
        }
        if (clazz == String.class) {
            return clazz.cast(value);
        }
        if (clazz == Long.class) {
            return clazz.cast(Long.valueOf(value));
        }
        if (clazz == Integer.class) {
            return clazz.cast(Integer.valueOf(value));
        }
        // 필요한 타입 변환 추가 가능
        throw new IllegalArgumentException("Unsupported class: " + clazz);
    }

    @Override
    public void deleteByKey(String token) {
        redisTemplate.delete(token);
    }

    public Boolean isRefreshTokenExists(String token) {
        return getValueByKey(token, String.class) != null;
    }
}
