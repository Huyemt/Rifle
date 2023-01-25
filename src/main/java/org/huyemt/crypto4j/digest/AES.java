package org.huyemt.crypto4j.digest;

import org.huyemt.crypto4j.Crypto4J;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * AES加密与解密
 *
 * AES encryption and decryption
 *
 * @author Huyemt
 */

public class AES {
    public AES() {

    }

    /**
     * 按照 密钥(Key)，加密模式(Mode)，补码(Padding)，偏移量(Iv) 加密原文
     *
     * The text is encrypted according to Key, Mode, Padding, and offset (Iv)
     *
     * @param content
     * @param key
     * @param mode
     * @param padding
     * @param iv
     * @return String
     */
    public String encrypt(byte[] content, Key key, Mode mode, Padding padding, Iv iv) {
        try {
            SecretKeySpec spec = new SecretKeySpec(key.getBytes(), "AES");

            if (mode == null) {
                mode = Mode.ECB;
            }

            if (padding == null) {
                padding = Padding.PKCS5_PADDING;
            }

            Cipher cipher = Cipher.getInstance("AES/" + mode + "/" + padding);

            if (iv != null) {
                cipher.init(Cipher.ENCRYPT_MODE, spec, new IvParameterSpec(iv.getBytes()));
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, spec);
            }

            return Crypto4J.Base64.encrypt(cipher.doFinal(content));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String encrypt(byte[] content, Key key, Mode mode, Padding padding, byte[] iv) {
        return encrypt(content, key, mode, padding, iv == null ? null : new Iv(iv));
    }

    public String encrypt(byte[] content, Key key, Mode mode, Padding padding, String iv) {
        return encrypt(content, key, mode, padding, iv == null ? null : new Iv(iv.getBytes(StandardCharsets.UTF_8)));
    }

    public String encrypt(byte[] content, Key key, Mode mode, Padding padding) {
        return encrypt(content, key, mode, padding, (Iv) null);
    }

    public String encrypt(byte[] content, Key key, Mode mode) {
        return encrypt(content, key, mode, Padding.PKCS5_PADDING, (Iv) null);
    }

    public String encrypt(byte[] content, Key key) {
        return encrypt(content, key, Mode.ECB, Padding.PKCS5_PADDING, (Iv) null);
    }

    public String encrypt(byte[] content, byte[] key, Mode mode, Padding padding, byte[] iv) {
        return encrypt(content, new Key(key), mode, padding, iv == null ? null : new Iv(iv));
    }

    public String encrypt(byte[] content, byte[] key, Mode mode, Padding padding, String iv) {
        return encrypt(content, new Key(key), mode, padding, iv == null ? null : new Iv(iv.getBytes(StandardCharsets.UTF_8)));
    }

    public String encrypt(byte[] content, byte[] key, Mode mode, Padding padding) {
        return encrypt(content, new Key(key), mode, padding, (Iv) null);
    }

    public String encrypt(byte[] content, byte[] key, Mode mode) {
        return encrypt(content, new Key(key), mode, Padding.PKCS5_PADDING, (Iv) null);
    }

    public String encrypt(byte[] content, byte[] key) {
        return encrypt(content, new Key(key), Mode.ECB, Padding.PKCS5_PADDING, (Iv) null);
    }

    ////////////////////////////////////////

    public String encrypt(byte[] content, String key, Mode mode, Padding padding, Iv iv) {
        return encrypt(content, new Key(key.getBytes(StandardCharsets.UTF_8)), mode, padding, iv);
    }

    public String encrypt(byte[] content, String key, Mode mode, Padding padding, byte[] iv) {
        return encrypt(content, new Key(key.getBytes(StandardCharsets.UTF_8)), mode, padding, iv == null ? null : new Iv(iv));
    }

    public String encrypt(byte[] content, String key, Mode mode, Padding padding, String iv) {
        return encrypt(content, new Key(key.getBytes(StandardCharsets.UTF_8)), mode, padding, iv == null ? null : new Iv(iv.getBytes(StandardCharsets.UTF_8)));
    }

    public String encrypt(byte[] content, String key, Mode mode, Padding padding) {
        return encrypt(content, new Key(key.getBytes(StandardCharsets.UTF_8)), mode, padding, (Iv) null);
    }

    public String encrypt(byte[] content, String key, Mode mode) {
        return encrypt(content, new Key(key.getBytes(StandardCharsets.UTF_8)), mode, Padding.PKCS5_PADDING, (Iv) null);
    }

    public String encrypt(byte[] content, String key) {
        return encrypt(content, new Key(key.getBytes(StandardCharsets.UTF_8)), Mode.ECB, Padding.PKCS5_PADDING, (Iv) null);
    }

    /////////////////////////////////////////

    public String encrypt(String content, Key key, Mode mode, Padding padding, Iv iv) {
        return encrypt(content.getBytes(StandardCharsets.UTF_8), key, mode, padding, iv);
    }

    public String encrypt(String content, Key key, Mode mode, Padding padding, byte[] iv) {
        return encrypt(content.getBytes(StandardCharsets.UTF_8), key, mode, padding, iv == null ? null : new Iv(iv));
    }

    public String encrypt(String content, Key key, Mode mode, Padding padding, String iv) {
        return encrypt(content.getBytes(StandardCharsets.UTF_8), key, mode, padding, iv == null ? null : new Iv(iv.getBytes(StandardCharsets.UTF_8)));
    }

    public String encrypt(String content, Key key, Mode mode, Padding padding) {
        return encrypt(content.getBytes(StandardCharsets.UTF_8), key, mode, padding, (Iv) null);
    }

    public String encrypt(String content, Key key, Mode mode) {
        return encrypt(content.getBytes(StandardCharsets.UTF_8), key, mode, Padding.PKCS5_PADDING, (Iv) null);
    }

    public String encrypt(String content, Key key) {
        return encrypt(content.getBytes(StandardCharsets.UTF_8), key, Mode.ECB, Padding.PKCS5_PADDING, (Iv) null);
    }

    public String encrypt(String content, byte[] key, Mode mode, Padding padding, byte[] iv) {
        return encrypt(content.getBytes(StandardCharsets.UTF_8), new Key(key), mode, padding, iv == null ? null : new Iv(iv));
    }

    public String encrypt(String content, byte[] key, Mode mode, Padding padding, String iv) {
        return encrypt(content.getBytes(StandardCharsets.UTF_8), new Key(key), mode, padding, iv == null ? null : new Iv(iv.getBytes(StandardCharsets.UTF_8)));
    }

    public String encrypt(String content, byte[] key, Mode mode, Padding padding) {
        return encrypt(content.getBytes(StandardCharsets.UTF_8), new Key(key), mode, padding, (Iv) null);
    }

    public String encrypt(String content, byte[] key, Mode mode) {
        return encrypt(content.getBytes(StandardCharsets.UTF_8), new Key(key), mode, Padding.PKCS5_PADDING, (Iv) null);
    }

    public String encrypt(String content, byte[] key) {
        return encrypt(content.getBytes(StandardCharsets.UTF_8), new Key(key), Mode.ECB, Padding.PKCS5_PADDING, (Iv) null);
    }

    ////////////////////////////////////////

    public String encrypt(String content, String key, Mode mode, Padding padding, Iv iv) {
        return encrypt(content.getBytes(StandardCharsets.UTF_8), new Key(key.getBytes(StandardCharsets.UTF_8)), mode, padding, iv);
    }

    public String encrypt(String content, String key, Mode mode, Padding padding, byte[] iv) {
        return encrypt(content.getBytes(StandardCharsets.UTF_8), new Key(key.getBytes(StandardCharsets.UTF_8)), mode, padding, iv == null ? null : new Iv(iv));
    }

    public String encrypt(String content, String key, Mode mode, Padding padding, String iv) {
        return encrypt(content.getBytes(StandardCharsets.UTF_8), new Key(key.getBytes(StandardCharsets.UTF_8)), mode, padding, iv == null ? null : new Iv(iv.getBytes(StandardCharsets.UTF_8)));
    }

    public String encrypt(String content, String key, Mode mode, Padding padding) {
        return encrypt(content.getBytes(StandardCharsets.UTF_8), new Key(key.getBytes(StandardCharsets.UTF_8)), mode, padding, (Iv) null);
    }

    public String encrypt(String content, String key, Mode mode) {
        return encrypt(content.getBytes(StandardCharsets.UTF_8), new Key(key.getBytes(StandardCharsets.UTF_8)), mode, Padding.PKCS5_PADDING, (Iv) null);
    }

    public String encrypt(String content, String key) {
        return encrypt(content.getBytes(StandardCharsets.UTF_8), new Key(key.getBytes(StandardCharsets.UTF_8)), Mode.ECB, Padding.PKCS5_PADDING, (Iv) null);
    }

    ///////////////////////////////////////////////
    ///////////////////////////////////////////////
    ///////////////////////////////////////////////

    public String decrypt(byte[] content, Key key, Mode mode, Padding padding, Iv iv) {
        try {
            SecretKeySpec spec = new SecretKeySpec(key.getBytes(), "AES");

            if (mode == null) {
                mode = Mode.ECB;
            }

            if (padding == null) {
                padding = Padding.PKCS5_PADDING;
            }

            Cipher cipher = Cipher.getInstance("AES/" + mode + "/" + padding);

            if (iv != null) {
                cipher.init(Cipher.DECRYPT_MODE, spec, new IvParameterSpec(iv.getBytes()));
            } else {
                cipher.init(Cipher.DECRYPT_MODE, spec);
            }

            return new String(cipher.doFinal(Crypto4J.Base64.decode(content)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decrypt(byte[] content, Key key, Mode mode, Padding padding, byte[] iv) {
        return decrypt(content, key, mode, padding, iv == null ? null : new Iv(iv));
    }

    public String decrypt(byte[] content, Key key, Mode mode, Padding padding, String iv) {
        return decrypt(content, key, mode, padding, iv == null ? null : new Iv(iv.getBytes(StandardCharsets.UTF_8)));
    }

    public String decrypt(byte[] content, Key key, Mode mode, Padding padding) {
        return decrypt(content, key, mode, padding, (Iv) null);
    }

    public String decrypt(byte[] content, Key key, Mode mode) {
        return decrypt(content, key, mode, Padding.PKCS5_PADDING, (Iv) null);
    }

    public String decrypt(byte[] content, Key key) {
        return decrypt(content, key, Mode.ECB, Padding.PKCS5_PADDING, (Iv) null);
    }

    public String decrypt(byte[] content, byte[] key, Mode mode, Padding padding, byte[] iv) {
        return decrypt(content, new Key(key), mode, padding, iv == null ? null : new Iv(iv));
    }

    public String decrypt(byte[] content, byte[] key, Mode mode, Padding padding, String iv) {
        return decrypt(content, new Key(key), mode, padding, iv == null ? null : new Iv(iv.getBytes(StandardCharsets.UTF_8)));
    }

    public String decrypt(byte[] content, byte[] key, Mode mode, Padding padding) {
        return decrypt(content, new Key(key), mode, padding, (Iv) null);
    }

    public String decrypt(byte[] content, byte[] key, Mode mode) {
        return decrypt(content, new Key(key), mode, Padding.PKCS5_PADDING, (Iv) null);
    }

    public String decrypt(byte[] content, byte[] key) {
        return decrypt(content, new Key(key), Mode.ECB, Padding.PKCS5_PADDING, (Iv) null);
    }

    ////////////////////////////////////////

    public String decrypt(byte[] content, String key, Mode mode, Padding padding, Iv iv) {
        return decrypt(content, new Key(key.getBytes(StandardCharsets.UTF_8)), mode, padding, iv);
    }

    public String decrypt(byte[] content, String key, Mode mode, Padding padding, byte[] iv) {
        return decrypt(content, new Key(key.getBytes(StandardCharsets.UTF_8)), mode, padding, iv == null ? null : new Iv(iv));
    }

    public String decrypt(byte[] content, String key, Mode mode, Padding padding, String iv) {
        return decrypt(content, new Key(key.getBytes(StandardCharsets.UTF_8)), mode, padding, iv == null ? null : new Iv(iv.getBytes(StandardCharsets.UTF_8)));
    }

    public String decrypt(byte[] content, String key, Mode mode, Padding padding) {
        return decrypt(content, new Key(key.getBytes(StandardCharsets.UTF_8)), mode, padding, (Iv) null);
    }

    public String decrypt(byte[] content, String key, Mode mode) {
        return decrypt(content, new Key(key.getBytes(StandardCharsets.UTF_8)), mode, Padding.PKCS5_PADDING, (Iv) null);
    }

    public String decrypt(byte[] content, String key) {
        return decrypt(content, new Key(key.getBytes(StandardCharsets.UTF_8)), Mode.ECB, Padding.PKCS5_PADDING, (Iv) null);
    }

    /////////////////////////////////////////

    public String decrypt(String content, Key key, Mode mode, Padding padding, Iv iv) {
        return decrypt(content.getBytes(StandardCharsets.UTF_8), key, mode, padding, iv);
    }

    public String decrypt(String content, Key key, Mode mode, Padding padding, byte[] iv) {
        return decrypt(content.getBytes(StandardCharsets.UTF_8), key, mode, padding, iv == null ? null : new Iv(iv));
    }

    public String decrypt(String content, Key key, Mode mode, Padding padding, String iv) {
        return decrypt(content.getBytes(StandardCharsets.UTF_8), key, mode, padding, iv == null ? null : new Iv(iv.getBytes(StandardCharsets.UTF_8)));
    }

    public String decrypt(String content, Key key, Mode mode, Padding padding) {
        return decrypt(content.getBytes(StandardCharsets.UTF_8), key, mode, padding, (Iv) null);
    }

    public String decrypt(String content, Key key, Mode mode) {
        return decrypt(content.getBytes(StandardCharsets.UTF_8), key, mode, Padding.PKCS5_PADDING, (Iv) null);
    }

    public String decrypt(String content, Key key) {
        return decrypt(content.getBytes(StandardCharsets.UTF_8), key, Mode.ECB, Padding.PKCS5_PADDING, (Iv) null);
    }

    public String decrypt(String content, byte[] key, Mode mode, Padding padding, byte[] iv) {
        return decrypt(content.getBytes(StandardCharsets.UTF_8), new Key(key), mode, padding, iv == null ? null : new Iv(iv));
    }

    public String decrypt(String content, byte[] key, Mode mode, Padding padding, String iv) {
        return decrypt(content.getBytes(StandardCharsets.UTF_8), new Key(key), mode, padding, iv == null ? null : new Iv(iv.getBytes(StandardCharsets.UTF_8)));
    }

    public String decrypt(String content, byte[] key, Mode mode, Padding padding) {
        return decrypt(content.getBytes(StandardCharsets.UTF_8), new Key(key), mode, padding, (Iv) null);
    }

    public String decrypt(String content, byte[] key, Mode mode) {
        return decrypt(content.getBytes(StandardCharsets.UTF_8), new Key(key), mode, Padding.PKCS5_PADDING, (Iv) null);
    }

    public String decrypt(String content, byte[] key) {
        return decrypt(content.getBytes(StandardCharsets.UTF_8), new Key(key), Mode.ECB, Padding.PKCS5_PADDING, (Iv) null);
    }

    ////////////////////////////////////////

    /**
     * 按照 加密模式(Mode)，补码(Padding)，偏移量(Iv) 解密密文
     *
     * Decrypt the ciphertext according to Mode, Padding and offset (Iv)
     *
     * @param content
     * @param key
     * @param mode
     * @param padding
     * @param iv
     * @return String
     */
    public String decrypt(String content, String key, Mode mode, Padding padding, Iv iv) {
        return decrypt(content.getBytes(StandardCharsets.UTF_8), new Key(key.getBytes(StandardCharsets.UTF_8)), mode, padding, iv);
    }

    public String decrypt(String content, String key, Mode mode, Padding padding, byte[] iv) {
        return decrypt(content.getBytes(StandardCharsets.UTF_8), new Key(key.getBytes(StandardCharsets.UTF_8)), mode, padding, iv == null ? null : new Iv(iv));
    }

    public String decrypt(String content, String key, Mode mode, Padding padding, String iv) {
        return decrypt(content.getBytes(StandardCharsets.UTF_8), new Key(key.getBytes(StandardCharsets.UTF_8)), mode, padding, iv == null ? null : new Iv(iv.getBytes(StandardCharsets.UTF_8)));
    }

    public String decrypt(String content, String key, Mode mode, Padding padding) {
        return decrypt(content.getBytes(StandardCharsets.UTF_8), new Key(key.getBytes(StandardCharsets.UTF_8)), mode, padding, (Iv) null);
    }

    public String decrypt(String content, String key, Mode mode) {
        return decrypt(content.getBytes(StandardCharsets.UTF_8), new Key(key.getBytes(StandardCharsets.UTF_8)), mode, Padding.PKCS5_PADDING, (Iv) null);
    }

    public String decrypt(String content, String key) {
        return decrypt(content.getBytes(StandardCharsets.UTF_8), new Key(key.getBytes(StandardCharsets.UTF_8)), Mode.ECB, Padding.PKCS5_PADDING, (Iv) null);
    }



    public enum Mode {
        ECB("ECB"),
        CBC("CBC"),
        CTR("CTR"),
        OFB("OFB"),
        CFB("CFB");

        private final String mode;
        Mode(String mode) {
            this.mode = mode;
        }

        public String getMode() {
            return mode;
        }

        @Override
        public String toString() {
            return getMode();
        }
    }

    public enum Padding {
        PKCS5_PADDING("PKCS5Padding"),
        ISO10126_PADDING("ISO10126Padding"),
        NO_PADDING("NoPadding");

        private final String padding;
        Padding(String padding) {
            this.padding = padding;
        }

        public String getPadding() {
            return padding;
        }

        @Override
        public String toString() {
            return getPadding();
        }
    }

    public static class Iv {
        public Charset ENCODING = StandardCharsets.UTF_8;
        public byte[] SEED;

        public Iv(byte[] seed) {
            SEED = seed;
        }

        public Iv(byte[] seed, Charset charset) {
            SEED = seed;
            ENCODING = charset;
        }

        public byte[] getBytes() {
            return SEED;
        }

        @Override
        public String toString() {
            return new String(SEED, ENCODING);
        }
    }

    public static class Key {
        public Charset ENCODING = StandardCharsets.UTF_8;
        public byte[] CONTENT;

        public Key(byte[] content) {
            CONTENT = content;
        }

        public Key(byte[] content, Charset charset) {
            CONTENT = content;
            ENCODING = charset;
        }

        public byte[] getBytes() {
            return CONTENT;
        }

        @Override
        public String toString() {
            return new String(CONTENT, ENCODING);
        }
    }
}
