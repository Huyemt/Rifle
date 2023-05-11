package org.bullet;

import java.util.Arrays;

/**
 * @author Huyemt
 */

public class HexUtil {
    public static byte[] toBytes(int v) {
        byte[] result = new byte[4];

        result[0] = (byte) ((v >> 24) & 0xFF);
        result[1] = (byte) ((v >> 16) & 0xFF);
        result[2] = (byte) ((v >> 8) & 0xFF);
        result[3] = (byte) (v & 0xFF);

        return result;
    }

    public static byte[] toBytes(short v) {
        byte[] result = new byte[2];

        result[1] = (byte) (v & 0xFF);

        result[0] = (byte) ((v >> 8) & 0xFF);

        return result;
    }

    public static byte[] toBytes(short[] vs) {
        byte[] result = new byte[vs.length * 2];

        for (int i = 0, j = 0; i < result.length; j++) {
            result[i] = (byte) ((vs[j] >> 8) & 0xFF);
            result[i + 1] = (byte) (vs[j] & 0xFF);
            i += 2;
        }

        return result;
    }

    public static byte[] toBytes(long num) {
        byte[] b = new byte[8];

        for (int i = 0; i < 8; i++) b[i] = (byte) (num >>> (56 - i * 8));

        return b;
    }

    public static long toLong(byte[] bytes) {
        long result = 0;

        for (int i = bytes.length - 1; i >= 0; i--) result = (result << 8) | (bytes[i] & 0xFF);

        return result;
    }

    public static int toInt(byte[] bytes) {
        int v = 0;

        for (int i = 0; i < 4; i++) v += (bytes[i] & 0xFF) << (3 - i) * 8;

        return v;
    }

    public static short toShort(byte[] b) {
        return (short) (b[1] & 0xFF | (b[0] & 0xFF) << 8);
    }

    public static short[] toShorts(byte[] bytes) {
        short[] result = new short[bytes.length / 2];

        for (int i = 0, j = 0; i < result.length; i++) {
            result[i] = toShort(Arrays.copyOfRange(bytes, j, j + 2));
            j += 2;
        }

        return result;
    }

    public static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            int value = b & 0xFF;
            String strHex = Integer.toHexString(value);

            if (strHex.length() < 2) strHex = "0" + strHex;

            sb.append(strHex);
        }

        return sb.toString();
    }
}
