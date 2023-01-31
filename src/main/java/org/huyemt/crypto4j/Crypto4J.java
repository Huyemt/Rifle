package org.huyemt.crypto4j;

import org.huyemt.crypto4j.digest.*;

/**
 * 加密库
 *
 * Crypto library
 *
 * @author Huyemt
 */

public class Crypto4J {
    public static final Utf8 Utf8 = new Utf8();

    public static final Base64 Base64 = new Base64();
    public static final Unicode Unicode = new Unicode();
    public static final MD5 MD5 = new MD5();
    public static final SHA SHA1 = new SHA();
    public static final SHA SHA256 = new SHA("256");
    public static final SHA SHA512 = new SHA("512");
    public static final AES AES = new AES();
    public static final RSA RSA = new RSA();

    private Crypto4J() {

    }
}
