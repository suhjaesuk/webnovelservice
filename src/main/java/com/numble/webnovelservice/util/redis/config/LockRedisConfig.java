package com.numble.webnovelservice.util.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
public class LockRedisConfig {
    @Value("${spring.redisson.address}")
    private String address;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() {

        Config config = new Config();
        config.useSingleServer()
                .setAddress(address);

        return Redisson.create(config);
    }
}
