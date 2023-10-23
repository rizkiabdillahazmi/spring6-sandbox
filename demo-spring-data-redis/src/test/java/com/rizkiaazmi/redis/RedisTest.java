package com.rizkiaazmi.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void redisTemplate() {
        assertNotNull(redisTemplate);
    }

    @Test
    void string() throws InterruptedException {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set("name", "Rizki", Duration.ofSeconds(2));
        assertEquals("Rizki", operations.get("name"));

        Thread.sleep(Duration.ofSeconds(3));
        assertNull(operations.get("name"));
    }

    @Test
    void list() {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();

        listOperations.rightPush("names", "Rizki");
        listOperations.rightPush("names", "Abdillah");
        listOperations.rightPush("names", "Azmi");

        assertEquals("Rizki", listOperations.leftPop("names"));
        assertEquals("Abdillah", listOperations.leftPop("names"));
        assertEquals("Azmi", listOperations.leftPop("names"));
    }
}
