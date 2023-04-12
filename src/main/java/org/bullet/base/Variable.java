package org.bullet.base;

/**
 * @author Huyemt
 */

public class Variable {
    public String name;
    public boolean canChange;
    public Object value;

    public Variable() {
        name = null;
        canChange = true;
        value = null;
    }
}
