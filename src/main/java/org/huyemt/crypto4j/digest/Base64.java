package org.huyemt.crypto4j.digest;

import java.nio.charset.StandardCharsets;

/**
 * Base64
 *
 * @author Huyemt
 */

public class Base64 {
    public static final java.util.Base64.Encoder encoder;
    public static final java.util.Base64.Encoder mimeEncoder;
    public static final java.util.Base64.Decoder decoder;
    public static final java.util.Base64.Decoder mimeDecoder;

    static {
        encoder = java.util.Base64.getEncoder();
        mimeEncoder = java.util.Base64.getMimeEncoder();
        decoder = java.util.Base64.getDecoder();
        mimeDecoder = java.util.Base64.getMimeDecoder();
    }

    public Base64() {

    }

    public byte[] encode(byte[] content) {
        return encoder.encode(content);
    }

    public byte[] encode(String content) {
        return encode(content.getBytes(StandardCharsets.UTF_8));
    }

    public byte[] decode(byte[] content) {
        return decoder.decode(content);
    }

    public byte[] decode(String content) {
        return decoder.decode(content);
    }

    public String encrypt(byte[] content) {
        return encoder.encodeToString(content);
    }

    public String encrypt(String content) {
        return encoder.encodeToString(content.getBytes(StandardCharsets.UTF_8));
    }

    public String decrypt(byte[] content) {
        return new String(decode(content), StandardCharsets.UTF_8);
    }

    public String decrypt(String content) {
        return new String(decode(content), StandardCharsets.UTF_8);
    }
}
