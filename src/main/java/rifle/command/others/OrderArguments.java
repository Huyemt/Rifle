package rifle.command.others;

import java.util.Arrays;

/**
 * @author Huyemt
 */

public class OrderArguments extends Arguments {
    protected String[] arguments;

    public OrderArguments(String arguments) {
        super(arguments);
        parse();
    }

    /**
     * Spilt origin_string to be string array by space and put them into arguments-array
     */
    private void parse() {
        this.arguments = !this.origin.contains(" ") ? new String[]{this.origin} : this.origin.split(" ");
    }

    public final String[] getArguments() {
        return arguments.clone();
    }
}
