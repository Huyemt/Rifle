package org.bullet;

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

    public static int bytes2Int(byte[] bytes) {
        int v = 0;

        for (int i = 0; i < 4; i++) v += (bytes[i] & 0xFF) << (3 - i) * 8;

        return v;
    }

    public static String bytes2HexStr(byte[] bytes) {
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
