package org.dong.util;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.digest.DigestUtil;
import java.math.BigInteger;
import java.security.SecureRandom;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;

@UtilityClass
public class PakeUtil {
  private static final String n =
      "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF43";
  private static final int RADIX = 16;

  public static String randomHex(int size) {
    SecureRandom rnd = ThreadLocal.withInitial(SecureRandom::new).get();
    byte[] b = new byte[size];
    rnd.nextBytes(b);
    return HexUtil.encodeHexStr(b);
  }

  public static String genPriKeyHex(String password, String salt) {
    byte[] priKey =
        new BigInteger(DigestUtil.sha256(password.getBytes()))
            .modPow(new BigInteger(salt, 16), new BigInteger(n, 16))
            .toByteArray();
    return HexUtil.encodeHexStr(priKey);
  }

  public static String genSeed(String password, String r) {
    return HexUtil.encodeHexStr(
        new BigInteger(DigestUtil.sha256(password.getBytes()))
            .modPow(new BigInteger(r, 16), new BigInteger(n, 16))
            .toByteArray());
  }

  public static String genPubKeyHex(String priKeyHex) {
    Ed25519PrivateKeyParameters priKey =
        new Ed25519PrivateKeyParameters(HexUtil.decodeHex(priKeyHex), 0);
    byte[] pubKey = priKey.generatePublicKey().getEncoded();
    return HexUtil.encodeHexStr(pubKey);
  }

  public static String genNonce(String cnonce, String snonce) {
    if (StringUtils.isAnyBlank(snonce, cnonce)) {
      throw new IllegalArgumentException();
    }
    return HexUtil.encodeHexStr(DigestUtil.sha256(cnonce + snonce));
  }

  public static String sign(String priKeyHex, String nonce) throws CryptoException {
    Ed25519PrivateKeyParameters priKey =
        new Ed25519PrivateKeyParameters(HexUtil.decodeHex(priKeyHex), 0);
    Signer signer = new Ed25519Signer();
    signer.init(true, priKey);
    byte[] data = HexUtil.decodeHex(nonce);
    signer.update(data, 0, data.length);
    return HexUtil.encodeHexStr(signer.generateSignature());
  }

  public static boolean verify(String verifier, String nonce, String proof) {
    Ed25519PublicKeyParameters pubKey =
        new Ed25519PublicKeyParameters(HexUtil.decodeHex(verifier), 0);
    Signer signer = new Ed25519Signer();
    signer.init(false, pubKey);
    byte[] data = HexUtil.decodeHex(nonce);
    signer.update(data, 0, data.length);
    return signer.verifySignature(HexUtil.decodeHex(proof));
  }

  public static String genSeeded(String seed, String salt) {
    return HexUtil.encodeHexStr(
        new BigInteger(seed, RADIX)
            .modPow(new BigInteger(salt, RADIX), new BigInteger(n, RADIX))
            .toByteArray());
  }
}
