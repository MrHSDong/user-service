package org.dong.config;

import io.vertx.core.Vertx;
import io.vertx.mysqlclient.MySQLBuilder;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.SqlClient;
import lombok.extern.slf4j.Slf4j;
import org.dong.properties.VertxProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class VertxConfig {

  @Bean
  MySQLConnectOptions connectOptions(VertxProperties properties) {
    log.info("[properties] {}", properties);
    return new MySQLConnectOptions()
        .setHost(properties.getHost())
        .setPort(properties.getPort())
        .setDatabase(properties.getDatabase())
        .setUser(properties.getUser())
        .setPassword(properties.getPassword());
  }

  @Bean
  SqlClient client(MySQLConnectOptions connectOptions) {
    PoolOptions poolOptions = new PoolOptions().setMaxSize(10);
    return MySQLBuilder.client()
        .with(poolOptions)
        .connectingTo(connectOptions)
        .using(Vertx.vertx())
        .build();
  }
}
