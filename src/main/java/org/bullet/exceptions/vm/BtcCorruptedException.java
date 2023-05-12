package org.bullet.exceptions.vm;

import java.io.File;

/**
 * @author Huyemt
 */

public class BtcCorruptedException extends VMException {

    public BtcCorruptedException(File file) {
        super(String.format("Failed to load, Bullet bytecode file with path \"%s\" is corrupted", file.getAbsolutePath()));
    }


}
