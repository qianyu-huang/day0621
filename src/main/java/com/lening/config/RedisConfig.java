package com.lening.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        // redis序列化设置
        RedisSerializer<?> redisSerializer = new StringRedisSerializer();

        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);

        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {

        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(host);
        factory.setPort(port);
        // factory.setPassword(password);
        return factory;

    }

}
