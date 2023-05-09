package org.bullet.vm.utils;

import org.bullet.exceptions.vm.VMException;
import org.bullet.vm.structures.BtcProgram;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Huyemt
 */

public class BtcWriter {
    public static File generateFile(File file, BtcProgram program) throws IOException, VMException {
        if (!file.getName().matches("^.+\\.btc$")) throw new VMException(String.format("The file with path \"%s\" is not a Bullet bytecode file", file.getAbsolutePath()));

        if (file.exists() && file.isDirectory()) throw new VMException(String.format("The path \"%s\" is a folder", file.getAbsolutePath()));
        if (!file.exists()) file.createNewFile();

        FileOutputStream outputStream = new FileOutputStream(file);

        outputStream.write(program.headers.SIGNATURE);
        outputStream.write(program.headers.VERSION);
        outputStream.write(program.headers.BIRTH);

        outputStream.flush();
        outputStream.close();

        return file;
    }
}
