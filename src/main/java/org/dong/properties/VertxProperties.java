package org.dong.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@ConfigurationProperties("io.vertx")
@Data
@RefreshScope
public class VertxProperties {
  private Integer port = 3306;
  private String host = "localhost";
  private String database = "database";
  private String user = "";
  private String password = "";
}
