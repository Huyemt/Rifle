package org.huyemt.crypto4j;

import org.huyemt.crypto4j.digest.*;

import java.security.NoSuchAlgorithmException;

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
    public static final Caesar Caesar = new Caesar();
    public static final MD5 MD5;

    public static final SHA SHA1;

    public static final SHA SHA256;
    public static final SHA SHA512;
    public static final AES AES = new AES();
    public static final RSA RSA = new RSA();
    public static final URL URL = new URL();

    static {
        try {
            MD5 = new MD5();
            SHA1 = new SHA();
            SHA256 = new SHA("256");
            SHA512 = new SHA("512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private Crypto4J() {

    }
}
