package org.huyemt.crypto4j.digest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Huyemt
 */

public class Digest {
    protected MessageDigest digest;

    public Digest(String algorithm) {
        MessageDigest digest1;
        try {
            digest1 = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            digest1 = null;
        }
        digest = digest1;
    }

    public MessageDigest getMessageDigest() {
        return digest;
    }

}
