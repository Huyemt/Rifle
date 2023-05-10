package org.bullet.vm.structures;

/**
 * @author Huyemt
 */

public interface BtcType {

    byte NULL = (byte) 0x00;
    byte BOOLEAN = (byte) 0x01;
    byte NUMBER = (byte) 0x02;
    byte SHORT_STRING = (byte) 0x03;
    byte LONG_STRING = (byte) 0x04;

}
