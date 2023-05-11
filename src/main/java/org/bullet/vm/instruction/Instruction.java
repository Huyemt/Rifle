package org.bullet.vm.instruction;

/**
 * @author Huyemt
 */

public class Instruction {
    // 262143
    public static final int MAXARG_BX = (1 << 18) - 1;

    // 131071
    public static final int MAXARG_SBX = MAXARG_BX >> 1;

    public static int getA(int i) {
        return (i >> 6) & 0xFF;
    }

    public static int getB(int i) {
        return (i >> 23) & 0x1FF;
    }

    public static int getC(int i) {
        return (i >> 14) & 0x1FF;
    }

    public static int getAx(int i) {
        return i >>> 6;
    }

    public static int getBx(int i) {
        return i >>> 14;
    }

    public static int getSBx(int i) {
        return getBx(i) - MAXARG_SBX;
    }
}
