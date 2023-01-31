package org.huyemt.crypto4j.digest;


import org.huyemt.crypto4j.Crypto4J;

/**
 * @author Huyemt
 */

public class Unicode {
    public Unicode() {

    }

    public byte[] encode(byte[] content) {
        StringBuilder builder = new StringBuilder();
        char[] chars = Crypto4J.Utf8.toString(content).toCharArray();
        for (char aChar : chars) {
            String aHex = Integer.toHexString(aChar);

            if (aHex.length() <= 2) {
                aHex = "00" + aHex;
            }
            builder.append("\\u").append(aHex);
        }

        return Crypto4J.Utf8.parse(builder.toString());
    }

    public byte[] encode(char[] content) {
        StringBuilder builder = new StringBuilder();
        for (char aChar : content) {
            String aHex = Integer.toHexString(aChar);

            if (aHex.length() <= 2) {
                aHex = "00" + aHex;
            }
            builder.append("\\u").append(aHex);
        }

        return Crypto4J.Utf8.parse(builder.toString());
    }

    public byte[] encode(String content) {
        return encode(Crypto4J.Utf8.parse(content));
    }

    public byte[] decode(byte[] content) {
        String origin = Crypto4J.Utf8.toString(content);
        int start = 0;
        int end = 0;
        StringBuilder builder = new StringBuilder();

        while (start > -1) {
            end = origin.indexOf("\\u", start + 2);
            String charString = "";
            if (end == -1) {
                charString = origin.substring(start + 2);
            } else {
                charString = origin.substring(start + 2, end);
            }

            char letter = (char) Integer.parseInt(charString, 16);
            builder.append(letter);
            start = end;
        }

        return Crypto4J.Utf8.parse(builder.toString());
    }

    public byte[] decode(char[] content) {
        String origin = String.valueOf(content);
        int start = 0;
        int end = 0;
        StringBuilder builder = new StringBuilder();

        while (start > -1) {
            end = origin.indexOf("\\u", start + 2);
            String charString = "";
            if (end == -1) {
                charString = origin.substring(start + 2);
            } else {
                charString = origin.substring(start + 2, end);
            }

            char letter = (char) Integer.parseInt(charString, 16);
            builder.append(letter);
            start = end;
        }

        return Crypto4J.Utf8.parse(builder.toString());
    }

    public byte[] decode(String content) {
        int start = 0;
        int end = 0;
        StringBuilder builder = new StringBuilder();

        while (start > -1) {
            end = content.indexOf("\\u", start + 2);
            String charString = "";
            if (end == -1) {
                charString = content.substring(start + 2);
            } else {
                charString = content.substring(start + 2, end);
            }

            char letter = (char) Integer.parseInt(charString, 16);
            builder.append(letter);
            start = end;
        }

        return Crypto4J.Utf8.parse(builder.toString());
    }

    public String encrypt(byte[] content) {
        return Crypto4J.Utf8.toString(encode(content));
    }

    public String encrypt(char[] content) {
        return Crypto4J.Utf8.toString(encode(content));
    }

    public String encrypt(String content) {
        return Crypto4J.Utf8.toString(encode(content));
    }

    public String decrypt(byte[] content) {
        return Crypto4J.Utf8.toString(decode(content));
    }

    public String decrypt(char[] content) {
        return Crypto4J.Utf8.toString(decode(content));
    }

    public String decrypt(String content) {
        return Crypto4J.Utf8.toString(decode(content));
    }
}
