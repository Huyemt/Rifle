package org.bullet.vm.utils;

import org.bullet.HexUtil;
import org.bullet.base.types.BtNumber;
import org.bullet.exceptions.vm.VMException;
import org.bullet.vm.BtcVM;
import org.bullet.vm.structure.BtcProgram;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * @author Huyemt
 */

public class BtcWriter {
    private final File file;
    private int constantCount;
    private final ArrayList<Byte> constants;

    public BtcWriter(File file) throws IOException, VMException {
        if (!file.getName().matches("^.+\\.btc$")) throw new VMException(String.format("The file with path \"%s\" is not a Bullet bytecode file", file.getAbsolutePath()));

        if (file.exists() && file.isDirectory()) throw new VMException(String.format("The path \"%s\" is a folder", file.getAbsolutePath()));
        if (!file.exists()) file.delete();
        file.createNewFile();

        this.file = file;
        constantCount = 0;
        constants = new ArrayList<>();
    }

    public void save(short[] version) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(BtcVM.signature);
        outputStream.write(HexUtil.toBytes(version));
        outputStream.write(BtcVM.birth);

        // codes
        outputStream.write(HexUtil.toBytes(0));

        // constants
        outputStream.write(HexUtil.toBytes(constantCount));

        if (constantCount > 0) {
            for (byte b : constants) outputStream.write(b);
        }

        // variables
        outputStream.write(HexUtil.toBytes(0));

        // functions
        outputStream.write(HexUtil.toBytes(0));

        outputStream.flush();
        outputStream.close();
    }

    public <T> BtcWriter putConstant(T obj) throws VMException {
        if (obj == null) {
            constants.add((byte) BtcProgram.NULL);
            constantCount++;
        } else if (!(obj instanceof String || obj instanceof BtNumber))
            throw new VMException("Unsupport type");

        if (obj instanceof String) putString((String) obj);
        if (obj instanceof BtNumber) putNumber((BtNumber) obj);

        return this;
    }

    private BtcWriter putString(String str) {

        if (str == null)
            constants.add((byte) BtcProgram.NULL);
        else {
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
            int type = bytes.length == 0 || str.length() <= 65535 ? BtcProgram.SHORT_STRING : BtcProgram.LONG_STRING;

            constants.add((byte) type);
            intoList(type == BtcProgram.LONG_STRING ? HexUtil.toBytes((long) bytes.length) : HexUtil.toBytes(bytes.length));
            intoList(bytes);
        }

        constantCount++;

        return this;
    }

    private BtcWriter putNumber(BtNumber number) {

        if (number == null)
            constants.add((byte) BtcProgram.NULL);
        else {
            String str = number.toString();
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);

            constants.add((byte) BtcProgram.NUMBER);
            intoList(HexUtil.toBytes(bytes.length));
            intoList(bytes);
        }

        constantCount++;

        return this;
    }

    private void intoList(byte[] bytes) {
        for (byte b : bytes) constants.add(b);
    }
}
