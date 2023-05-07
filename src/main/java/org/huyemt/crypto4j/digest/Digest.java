package org.huyemt.crypto4j.digest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Huyemt
 */

public class Digest {
    protected MessageDigest digest;

    public Digest(String algorithm) throws NoSuchAlgorithmException {
        MessageDigest digest1 = MessageDigest.getInstance(algorithm);
    }

    public MessageDigest getMessageDigest() {
        return digest;
    }

}
