package org.dong.model;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

public class BaseModel {
  private Long _id;

  @Getter @Setter private Instant createdAt;
  @Getter @Setter private Instant updatedAt;

  public Long getId() {
    return this._id;
  }
}
