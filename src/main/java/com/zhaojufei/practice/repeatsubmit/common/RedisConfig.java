package com.zhaojufei.practice.repeatsubmit.common;

import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public ScmRedisTemplate scmRedisTemplate(RedisTemplate redisTemplate, RedissonClient redissonClient){
        ScmRedisTemplate template = new ScmRedisTemplate();
        redisTemplate.setKeySerializer(stringRedisSerializer());
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(stringRedisSerializer());
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer());
        template.setTemplate(redisTemplate);
        template.setRedissonClient(redissonClient);
        return template;
    }
    @Bean
    public RedisSerializer stringRedisSerializer(){
        return new StringRedisSerializer();
    }
    @Bean
    public RedisSerializer genericJackson2JsonRedisSerializer(){
        return new GenericJackson2JsonRedisSerializer();
    }
}
