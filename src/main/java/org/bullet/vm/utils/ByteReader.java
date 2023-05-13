package org.bullet.vm.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Huyemt
 */

public class ByteReader {
    private final ByteBuffer buffer;

    public ByteReader(byte[] bytes) {
        buffer = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN);
    }

    public String getString(int count, Charset charset) {
        return new String(getBytes(count), charset);
    }

    public String getString(int count) {
        return getString(count, StandardCharsets.UTF_8);
    }

    public boolean getBoolean() {
        return buffer.get() != 0x00;
    }

    public short getShort() {
        return buffer.getShort();
    }

    public int getInt() {
        return buffer.getInt();
    }

    public long getLong() {
        return buffer.getLong();
    }

    public char getChar() {
        return (char) buffer.get();
    }

    public double getDouble() {
        return buffer.getDouble();
    }

    public byte getByte() {
        return buffer.get();
    }

    public byte[] getBytes(int count) {
        byte[] result = new byte[count];

        buffer.get(result);

        return result;
    }

    public byte[] all() {
        return buffer.array();
    }
}
