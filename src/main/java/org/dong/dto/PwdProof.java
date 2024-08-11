package org.dong.dto;

// import jakarta.validation.constraints.NotBlank;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PwdProof {
  @NotBlank private String proof;
  @NotBlank private String snonce;
  @NotBlank private String cnonce;
}
