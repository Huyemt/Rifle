package org.bullet.vm.utils;

import org.bullet.HexUtil;
import org.bullet.base.types.BtNumber;
import org.bullet.exceptions.vm.VMBtcCorruptedException;
import org.bullet.exceptions.vm.VMException;
import org.bullet.vm.structures.BtcFunction;
import org.bullet.vm.structures.BtcType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author Huyemt
 */

public class BtcReader {

    public final File file;
    public final ByteBuffer buffer;

    public BtcReader(File file) throws IOException {
        this.file = file;

        try (FileInputStream inputStream = new FileInputStream(file)) {
            buffer = ByteBuffer.wrap(inputStream.readAllBytes()).order(ByteOrder.LITTLE_ENDIAN);
        }

    }

    public int readCode() {
        return readInt();
    }

    public int[] readCodes() {
        int[] codes = new int[buffer.getInt()];

        for (int i = 0; i < codes.length; i++) codes[i] = readInt();

        return codes;
    }

    public Object readConstant() throws VMException {
        switch (buffer.get()) {
            case BtcType.NULL:
                return null;
            case BtcType.BOOLEAN:
                return buffer.get() != 0;
            case BtcType.NUMBER:
                return new BtNumber(readShortString());
            case BtcType.SHORT_STRING:
            case BtcType.LONG_STRING:
                return readShortString();
        }

        throw new VMBtcCorruptedException(file);
    }

    public Object[] readConstants() throws VMException {
        Object[] r = new Object[readInt()];

        for (int i = 0; i < r.length; i++) r[i] = readConstant();

        return r;
    }

    public BtcFunction[] readFunctions() throws VMException {
        BtcFunction[] btcFunctions = new BtcFunction[readInt()];

        for (int i = 0; i < btcFunctions.length; i++) btcFunctions[i] = new BtcFunction(this);

        return btcFunctions;
    }

    public String readShortString() {
        return new String(readBytes(readInt()));
    }

    public String readLongString() {
        return new String(readBytes((int) readLong()));
    }

    public byte[] readBytes(int count) {
        byte[] r = new byte[count];

        buffer.get(r);

        return r;
    }

    public int readInt() {
        return HexUtil.toInt(readBytes(4));
    }

    public long readLong() {
        return HexUtil.toLong(readBytes(8));
    }
}
