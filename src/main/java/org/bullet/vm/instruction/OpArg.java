package org.bullet.vm.instruction;

/**
 * @author Huyemt
 */

public enum OpArg {
    /**
     * argument is not used
     */
    N,

    /**
     * argument is used
     */
    U,

    /**
     * argument is a register or a jump offset
     */
    R,

    /**
     * argument a constant or register/constant
     */
    K,
    ;
}
