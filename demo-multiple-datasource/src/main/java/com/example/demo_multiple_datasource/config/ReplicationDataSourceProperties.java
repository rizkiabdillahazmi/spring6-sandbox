package com.example.demo_multiple_datasource.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "spring.datasource.replication")
public record ReplicationDataSourceProperties(
        String username,
        String password,
        String driverClassName,
        Write write,
        List<Read> reads
) {
    @Data
    public static class Write {
        private String name;
        private String url;
    }

    @Data
    public static class Read {
        private String name;
        private String url;
    }
}
