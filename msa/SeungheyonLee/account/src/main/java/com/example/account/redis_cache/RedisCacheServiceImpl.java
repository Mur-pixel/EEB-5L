package com.example.account.redis_cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
public class RedisCacheServiceImpl implements RedisCacheService {

    // MSA 프로젝트에서 Lombok (Getter, Setter, 등등이 잘 안먹기 때문에 직접 주입하는 모습이 보임
    // @Autowired 혹은 @RequiredArgsConstructor
    private final StringRedisTemplate redisTemplate;

    // StringRedisTemplate은 Spring Data Redis에서 제공하는 Redis 연동 템플릿 클래스입니다.
    // Redis와 문자열 Kek, Value를 주고 받을 수 있습니다.
    // 참고로 StringRedisTemplate은 Bean 입니다. <- 그러니까 이미 싱글톤이겠구나
    public RedisCacheServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // Key와 Value를 저장하는 매서드
    // K Key와 V Value 형식으로 어떤 타입이던 수용할 수 있도록 구성하였음.
    // 잘 보면 setKeyAndValue가 두 개 있는데 이것을 뭐라고 할까요? 매서드 오버로딩
    // 매서드 오버로딩의 동작 조건은? 파라메터의 개수등등이 다르면 동작

    // Redis에서 얼마나 오래 정보를 관리할 것인가를 지정할 수 있는데
    // 별도의 지정이 없으면 하루의 절반 (12시간)으로 설정되고 있습니다.
    @Override
    public <K, V> void setKeyAndValue(K key, V value) {
        setKeyAndValue(key, value, Duration.ofMinutes(720));  // 기본 TTL 720분 적용
    }

    // 그리고 사용자가 원하는 형태로 지정을 하면
    // 아래와 같이 원하는 시간만큼 입력되도록 구성할 수 있습니다.
    // 결국 위의 코드는 Duration이 지정되지 않은 경우 디폴트 값을 지정하기 위한 트릭입니다.
    @Override
    public <K, V> void setKeyAndValue(K key, V value, Duration ttl) {
        // 이 매서드는 실제 Time To Live (TTL) 값을 직접 지정합니다.
        // 우선 어떤 형식이 오던 String으로 변환하여 입력하고 있습니다.
        // TTL이 지나면 Redis가 자동으로 Key를 삭제합니다.
        String keyAsString = String.valueOf(key);
        String valueAsString = String.valueOf(value);

        // redisTemplate.opsForValue()를 통해 ValueOperations 객체를 획득합니다.
        // 이 객체는 문자열 기반의 Redis 작업을 수행할 수 있도록 지원합니다.
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        // 위에서 문자열로 정리한 Key와 Value, 그리고 시간을 설정합니다.
        valueOps.set(keyAsString, valueAsString, ttl);
    }

    // Redis에 저장된 문자열 값을 꺼내서 호출자가 원하는 타입(clazz)으로 변환하여 리턴
    @Override
    public <T> T getValueByKey(String key, Class<T> clazz) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        // 획득한 ValueOperations 객체의 get을 사용하여 key 값에 해당하는 value를 획득
        String value = ops.get(key);
        if (value == null) {
            return null;
        }
        // 실제 리턴할 타입으로 캐스팅하기 위해 Type Check를 진행합니다.
        // 실제 타입 변환시 Float이나 Double은 사용할 일이 없다 생각하여 배제하였음.
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

    // Redis에 저장된 key 값 제거(사실 우리 관점에서는 사용자 토큰임)
    @Override
    public void deleteByKey(String token) {
        redisTemplate.delete(token);
    }

    // Refresh 용도인데 지금은 안 쓰기 때문에 배제
    public Boolean isRefreshTokenExists(String token) {
        return getValueByKey(token, String.class) != null;
    }
}
