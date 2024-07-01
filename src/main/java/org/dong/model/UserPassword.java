package org.dong.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserPassword extends BaseModel {
  private String openId;
  private String verifier;
  private String salt;
}
