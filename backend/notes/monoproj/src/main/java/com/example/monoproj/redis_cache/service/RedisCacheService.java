package com.example.monoproj.redis_cache.service;

import java.time.Duration;

public interface RedisCacheService {
    <K, V> void setKeyAndValue(K key, V value);
    <K, V> void setKeyAndValue(K key, V value, Duration ttl);
    // Long getValueByKey(String token);
    <T> T getValueByKey(String key, Class<T> clazz);
    void deleteByKey(String token);
}
