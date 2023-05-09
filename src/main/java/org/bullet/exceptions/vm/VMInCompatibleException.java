package org.bullet.exceptions.vm;

import org.bullet.vm.BtcVM;

import java.io.File;

/**
 * @author Huyemt
 */

public class VMInCompatibleException extends VMException {

    public VMInCompatibleException(File file, byte[] version) {
        super(String.format("Your virtual machine version(v%d.%d.%d) is lower than the bytecode version(v%d.%d.X) with path \"%s\"", (int) BtcVM.VERSION[0], (int) BtcVM.VERSION[1], (int) BtcVM.VERSION[2], (int) version[0], (int) version[1], file.getAbsolutePath()));
    }

}
