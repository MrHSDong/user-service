package org.dong.controller;

import cn.hutool.core.util.BooleanUtil;
import java.time.Duration;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dong.dto.PwdExchange;
import org.dong.dto.PwdProof;
import org.dong.dto.PwdSeed;
import org.dong.dto.PwdVerifier;
import org.dong.model.UserPassword;
import org.dong.repository.UserPasswordRepo;
import org.dong.util.PakeUtil;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1")
@Slf4j
@AllArgsConstructor
public class PasswordController {
  private UserPasswordRepo passwordRepo;
  private ReactiveStringRedisTemplate redisTemplate;

  @PostMapping("/user/{open_id}/password")
  public Mono<Integer> updatePassword(
      @PathVariable("open_id") String openId, @Valid @RequestBody PwdVerifier pwdVerifier) {
    log.info("openId:{} password:{}", openId, pwdVerifier);
    UserPassword userPassword = new UserPassword();
    userPassword.setOpenId(openId);
    userPassword.setVerifier(pwdVerifier.getVerifier());
    userPassword.setSalt(pwdVerifier.getSalt());
    return passwordRepo.update(userPassword);
  }

  @PostMapping("/user/{open_id}/password/exchange")
  public Mono<PwdExchange> exchange(
      @PathVariable("open_id") String openId, @Valid @RequestBody PwdSeed seed) {
    String snonce = PakeUtil.randomHex(16);
    return passwordRepo
        .getByOpenId(openId)
        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
        .flatMap(userPassword -> redisTemplate
            .opsForValue()
            .set("pake:snonce:" + openId, snonce, Duration.ofSeconds(200))
            .flatMap(success -> {
              if (BooleanUtil.isFalse(success)) {
                return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
              }
              return Mono.just(
                  new PwdExchange(
                      PakeUtil.genSeeded(seed.getSeed(), userPassword.getSalt()), snonce));
            }));
  }

  @PostMapping("/user/{open_id}/verify")
  public Mono<Boolean> verify(
      @PathVariable("open_id") String openId, @Valid @RequestBody PwdProof pwdProof) {
    return redisTemplate
        .opsForValue()
        .get("pake:snonce:" + openId)
        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.PRECONDITION_FAILED)))
        .flatMap(snonce -> passwordRepo
            .getByOpenId(openId)
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
            .map(userPassword -> PakeUtil.verify(
                userPassword.getVerifier(),
                PakeUtil.genNonce(pwdProof.getCnonce(), pwdProof.getSnonce()),
                pwdProof.getProof())));
  }
}
