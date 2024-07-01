package org.dong.util;

import org.bouncycastle.crypto.CryptoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PakeUtilTest {
  @Test
  void testRandomHex() {
    String s = PakeUtil.randomHex(16);
    System.out.println(s);
    Assertions.assertEquals(32, s.length());
  }

  @Test
  void testGenSeed() {
    String seed = PakeUtil.genSeed("Dxy175939.", "1");
    System.out.println(seed);
    Assertions.assertNotNull(seed);
  }

  @Test
  void testGenPriKeyHex() {
    String password = "Dxy175939.";
    String salt = "737565e1146a24fb36478d80520806da";
    String priKeyHex = PakeUtil.genPriKeyHex(password, salt);
    System.out.println(priKeyHex);
    Assertions.assertNotNull(priKeyHex);
  }

  @Test
  void testGenPubKeyHex() {
    String priKeyHex = "3721cea2ab2c1fe1cb08ddc5f2fd66b4ca4f03ea38d98ddb75b5fb04f1dc1783";
    String verifier = PakeUtil.genPubKeyHex(priKeyHex);
    System.out.println(verifier);
    Assertions.assertNotNull(verifier);
  }

  @Test
  void testGenNonce() {
    String cnonce = "5e50e9bfdc0f20a9545e78bfbcbcd5e8";
    String snonce = "4bdca43ce5ccc73ff9956e861810c829";
    String nonce = PakeUtil.genNonce(cnonce, snonce);
    System.out.println(nonce);
    Assertions.assertNotNull(nonce);
  }

  @Test
  void testSignVerify() throws CryptoException {
    String priKeyHex = "3721cea2ab2c1fe1cb08ddc5f2fd66b4ca4f03ea38d98ddb75b5fb04f1dc1783";
    String cnonce = "5e50e9bfdc0f20a9545e78bfbcbcd5e8";
    String snonce = "ca6d3e7f55b6228a4da11723e4b31bcc";
    String nonce = PakeUtil.genNonce(cnonce, snonce);
    String sign = PakeUtil.sign(priKeyHex, nonce);
    System.out.println(sign);

    String verifier = "4fbd3dc2686eeb94870cdb25a2376085f37054533f1b9a5977a79ddcf73cb20a";
    System.out.println(PakeUtil.verify(verifier, nonce, sign));
  }
}
