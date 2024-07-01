package org.dong.util;

import cn.hutool.core.codec.Base62;
import java.security.SecureRandom;
import lombok.experimental.UtilityClass;

@UtilityClass
public class IdUtil {

  public static String genBase62Id() {
    SecureRandom rnd = ThreadLocal.withInitial(SecureRandom::new).get();
    byte[] b = new byte[16];
    rnd.nextBytes(b);
    return Base62.encode(b);
  }
}
