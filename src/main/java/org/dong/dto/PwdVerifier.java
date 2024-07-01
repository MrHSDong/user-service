package org.dong.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PwdVerifier {
  @NotBlank private String verifier;
  @NotBlank private String salt;
}
