package org.dong.repository.impl;

import io.vertx.sqlclient.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import org.dong.model.UserPassword;
import org.dong.repository.UserPasswordRepo;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class UserPasswordRepoImpl implements UserPasswordRepo {
  private SqlClient client;

  static Function<Row, UserPassword> mapper =
      row -> {
        UserPassword password = new UserPassword();
        password.setOpenId(row.getString("open_id"));
        password.setVerifier(row.getString("verifier"));
        password.setSalt(row.getString("salt"));
        LocalDateTime createdAt = row.getLocalDateTime("created_at");
        password.setCreatedAt(
            Instant.from(row.getLocalDateTime("created_at").atOffset(ZoneOffset.ofHours(8))));

        password.setUpdatedAt(
            Instant.from(row.getLocalDateTime("updated_at").atOffset(ZoneOffset.ofHours(8))));
        return password;
      };

  @Override
  public Mono<UserPassword> getByOpenId(String openId) {
    String sql = "select * from t_user_password where open_id=?";
    return Mono.fromCompletionStage(
            client
                .preparedQuery(sql)
                .execute(Tuple.of(openId))
                .map(rowSet -> rowSet)
                .toCompletionStage())
        .flatMapMany(Flux::fromIterable)
        .map(mapper)
        .singleOrEmpty();
  }

  @Override
  public Mono<Integer> update(UserPassword userPassword) {
    String sql =
        " insert into t_user_password "
            + "(`open_id`, `verifier`, `salt`) values"
            + "(?, ?, ?) on duplicate key update `verifier`=?, `salt`=?; ";
    return Mono.fromCompletionStage(
        client
            .preparedQuery(sql)
            .execute(
                Tuple.of(
                    userPassword.getOpenId(),
                    userPassword.getVerifier(),
                    userPassword.getSalt(),
                    userPassword.getVerifier(),
                    userPassword.getSalt()))
            .map(SqlResult::rowCount)
            .toCompletionStage());
  }
}
