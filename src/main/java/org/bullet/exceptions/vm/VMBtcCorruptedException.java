package org.bullet.exceptions.vm;

import java.io.File;

/**
 * @author Huyemt
 */

public class VMBtcCorruptedException extends VMException {

    public VMBtcCorruptedException(File file) {
        super(String.format("Failed to load, Bullet bytecode file with path \"%s\" is corrupted", file.getAbsolutePath()));
    }


}
