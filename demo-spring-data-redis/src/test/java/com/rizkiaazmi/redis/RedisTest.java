package com.rizkiaazmi.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.util.Set;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
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
        ListOperations<String, String> operations = redisTemplate.opsForList();

        operations.rightPush("names", "Rizki");
        operations.rightPush("names", "Abdillah");
        operations.rightPush("names", "Azmi");

        assertEquals("Rizki", operations.leftPop("names"));
        assertEquals("Abdillah", operations.leftPop("names"));
        assertEquals("Azmi", operations.leftPop("names"));

        redisTemplate.delete("names");
    }

    @Test
    void set() {
        SetOperations<String, String> operations = redisTemplate.opsForSet();

        operations.add("students", "Rizki");
        operations.add("students", "Rizki");
        operations.add("students", "Rizki");
        operations.add("students", "Abdillah");
        operations.add("students", "Abdillah");
        operations.add("students", "Abdillah");
        operations.add("students", "Azmi");
        operations.add("students", "Azmi");
        operations.add("students", "Azmi");

        Set<String> students = operations.members("students");
        assertEquals(3, students.size());

        assertThat(students, hasItems("Rizki", "Abdillah", "Azmi"));

        redisTemplate.delete("students");
    }
}
