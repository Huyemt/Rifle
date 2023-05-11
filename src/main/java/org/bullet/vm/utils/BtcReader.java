package org.bullet.vm.utils;

import org.bullet.HexUtil;
import org.bullet.base.types.BtNumber;
import org.bullet.exceptions.vm.VMBtcCorruptedException;
import org.bullet.exceptions.vm.VMException;
import org.bullet.interpreter.BulletRuntime;
import org.bullet.vm.structure.components.BtcFunction;
import org.bullet.vm.structure.BtcType;
import org.bullet.vm.structure.components.BtcVariable;

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
        byte type = buffer.get();

        try {
            switch (type) {
                case BtcType.NULL:
                    return BulletRuntime.BTNULL;
                case BtcType.BOOLEAN:
                    return buffer.get() != 0;
                case BtcType.NUMBER:
                    return new BtNumber(readString(BtcType.SHORT_STRING));
                case BtcType.SHORT_STRING:
                case BtcType.LONG_STRING:
                    return readString(type);
            }
        } catch (Exception e) {
            throw new VMException(e.getMessage());
        }

        throw new VMBtcCorruptedException(file);
    }

    public Object[] readConstants() throws VMException {
        Object[] r = new Object[readInt()];

        for (int i = 0; i < r.length; i++) r[i] = readConstant();

        return r;
    }

    public BtcVariable[] readVariables() throws VMException {
        BtcVariable[] r = new BtcVariable[readInt()];

        for (int i = 0; i < r.length; i++) r[i] = new BtcVariable(this);

        return r;
    }

    public BtcFunction[] readFunctions() throws VMException {
        BtcFunction[] btcFunctions = new BtcFunction[readInt()];

        for (int i = 0; i < btcFunctions.length; i++) btcFunctions[i] = new BtcFunction(this);

        return btcFunctions;
    }

    public String readString(int type) throws VMException {
        int size;

        if (type == BtcType.LONG_STRING)
            size = (int) readLong();
        else if (type == BtcType.SHORT_STRING)
            size = readInt();
        else
            throw new VMBtcCorruptedException(file);

        if (size == 0) return "";

        return new String(readBytes(size));
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
