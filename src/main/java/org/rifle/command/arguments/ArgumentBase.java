package org.rifle.command.arguments;

/**
 * @author Huyemt
 */

public class ArgumentBase {
    protected final String origin;

    public ArgumentBase(String origin) {
        this.origin = origin;
    }

    public final String getOrigin() {
        return origin;
    }
}
