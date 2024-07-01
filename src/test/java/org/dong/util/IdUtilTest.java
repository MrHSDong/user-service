package org.dong.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IdUtilTest {
  @Test
  void testGenBase62Id() {
    String id = IdUtil.genBase62Id();
    System.out.println(id);
    Assertions.assertNotNull(id);
  }
}
