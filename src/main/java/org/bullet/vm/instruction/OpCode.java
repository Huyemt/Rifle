package org.bullet.vm.instruction;

import static org.bullet.vm.instruction.OpMode.*;
import static org.bullet.vm.instruction.OpArg.*;

/**
 * @author Huyemt
 */

public enum OpCode {
    ADD(0, 1, K, K, iABC),
    SUB(0, 1, K, K, iABC),
    MUL(0, 1, K, K, iABC),
    DIV(0, 1, K, K, iABC),
    POW(0, 1, K, K, iABC),
    MOD(0, 1, K, K, iABC),
    ;

    private final int testFlag;
    private final int setAFlag;
    private final OpArg argBMode;
    private final OpArg argCMode;
    private final OpMode opMode;

    OpCode(int testFlag, int setAFlag, OpArg argBMode, OpArg argCMode, OpMode opMode) {
        this.testFlag = testFlag;
        this.setAFlag = setAFlag;
        this.argBMode = argBMode;
        this.argCMode = argCMode;
        this.opMode = opMode;
    }
}
