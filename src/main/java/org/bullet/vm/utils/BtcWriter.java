package org.bullet.vm.utils;

import org.bullet.HexUtil;
import org.bullet.exceptions.vm.VMException;
import org.bullet.vm.BtcVM;
import org.bullet.vm.structures.BtcProgram;
import org.bullet.vm.structures.BtcType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Huyemt
 */

public class BtcWriter {
    public static File generateFile(File file, BtcProgram program) throws IOException, VMException {
        if (!file.getName().matches("^.+\\.btc$")) throw new VMException(String.format("The file with path \"%s\" is not a Bullet bytecode file", file.getAbsolutePath()));

        if (file.exists() && file.isDirectory()) throw new VMException(String.format("The path \"%s\" is a folder", file.getAbsolutePath()));
        if (!file.exists()) file.delete();
        file.createNewFile();

        FileOutputStream outputStream = new FileOutputStream(file);

        outputStream.write(BtcVM.signature);
        outputStream.write(HexUtil.toBytes(program.version));
        outputStream.write(BtcVM.birth);

        // codes
        outputStream.write(HexUtil.toBytes(0));

        // constants
        outputStream.write(HexUtil.toBytes(2));

        outputStream.write(BtcType.NUMBER);
        String n = "114514.11111";
        outputStream.write(HexUtil.toBytes(n.length()));
        outputStream.write(n.getBytes(StandardCharsets.UTF_8));

        outputStream.write(BtcType.SHORT_STRING);
        n = "Hello, world";
        outputStream.write(HexUtil.toBytes(n.length()));
        outputStream.write(n.getBytes(StandardCharsets.UTF_8));

        // functions
        outputStream.write(HexUtil.toBytes(0));

        outputStream.flush();
        outputStream.close();

        return file;
    }
}
