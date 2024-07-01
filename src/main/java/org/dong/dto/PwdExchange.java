package org.dong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PwdExchange {
  private String seeded;
  private String snonce;
}
