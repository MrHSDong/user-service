package org.dong.dto;

// import jakarta.validation.constraints.NotBlank;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PwdSeed {
  @NotBlank private String seed;
}
