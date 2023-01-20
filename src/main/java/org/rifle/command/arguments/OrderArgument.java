package org.rifle.command.arguments;

/**
 * @author Huyemt
 */

public class OrderArgument extends ArgumentBase {
    private String[] arguments;

    public OrderArgument(String origin) {
        super(origin.trim().replace("  ", " "));

        parse();
    }

    private void parse() {
        if (getOrigin() == null || getOrigin().length() == 0) {
            arguments = new String[0];
            return;
        }

        if (!getOrigin().contains(" ")) {
            arguments = new String[]{getOrigin()};
            return;
        }

        arguments = getOrigin().split(" ");
    }

    public String[] getArgs() {
        return arguments;
    }
}
