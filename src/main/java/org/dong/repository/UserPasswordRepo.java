package org.dong.repository;

import org.dong.model.UserPassword;
import reactor.core.publisher.Mono;

public interface UserPasswordRepo {
  Mono<Integer> update(UserPassword userPassword);

  Mono<UserPassword> getByOpenId(String openId);
}
