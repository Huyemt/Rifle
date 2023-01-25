package org.huyemt.crypto4j.digest;

import java.nio.charset.StandardCharsets;

/**
 * @author Huyemt
 */

public class Utf8 {
    public Utf8() {

    }

    public byte[] parse(String content) {
        return content.getBytes(StandardCharsets.UTF_8);
    }

    public String toString(byte[] content) {
        return new String(content, StandardCharsets.UTF_8);
    }
}
