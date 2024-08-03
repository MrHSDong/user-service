package org.dong.config;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.dong.properties.VertxProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({VertxProperties.class})
@EnableApolloConfig
public class PropertiesConfig {}
