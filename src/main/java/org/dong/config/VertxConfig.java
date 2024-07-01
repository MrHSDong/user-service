package org.dong.config;

import io.vertx.core.Vertx;
import io.vertx.mysqlclient.MySQLBuilder;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.SqlClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VertxConfig {

  @Bean
  MySQLConnectOptions connectOptions() {
    return new MySQLConnectOptions()
        .setHost("localhost")
        .setPort(3306)
        .setDatabase("user_db")
        .setUser("root")
        .setPassword("password");
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
