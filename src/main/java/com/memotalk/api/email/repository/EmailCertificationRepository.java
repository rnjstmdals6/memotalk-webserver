package com.memotalk.api.email.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@RequiredArgsConstructor
@Repository
public class EmailCertificationRepository {

    private static final String PREFIX = "email:";  // (1)
    private static final int LIMIT_TIME = 3 * 60;  // (2)

    private final StringRedisTemplate stringRedisTemplate;

    public void createEmailCertification(String email, String certificationNumber) { //(3)
        stringRedisTemplate.opsForValue()
                .set(PREFIX + email, certificationNumber, Duration.ofSeconds(LIMIT_TIME));
    }

    public String getEmailCertification(String email) { // (4)
        return stringRedisTemplate.opsForValue().get(PREFIX + email);
    }

    public void removeEmailCertification(String email) { // (5)
        stringRedisTemplate.delete(PREFIX + email);
    }

    public boolean hasKey(String email) {  //(6)
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(PREFIX + email));
    }
}
