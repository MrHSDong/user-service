package org.dong.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;

@Configuration
public class RedisConfig {

  @Bean
  LettuceConnectionFactory lettuceConnectionFactory() {

    return new LettuceConnectionFactory(new RedisStandaloneConfiguration("localhost", 6379));
  }

  @Bean
  ReactiveStringRedisTemplate reactiveStringRedisTemplate(
      LettuceConnectionFactory connectionFactory) {
    return new ReactiveStringRedisTemplate(connectionFactory);
  }
}
