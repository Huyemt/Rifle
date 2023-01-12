package rifle.command.others;

/**
 * @author Huyemt
 */

public class CommandArguments extends Arguments {
    private final KeyArguments keyMode;
    private final OrderArguments orderMode;

    public CommandArguments(String arguments) {
        super(arguments);

        this.keyMode = new KeyArguments(this.origin);
        this.orderMode = new OrderArguments(this.origin);
    }

    /**
     * Use key mode
     * @return KeyArguments
     */
    public final KeyArguments toKeyMode() {
        return this.keyMode;
    }

    /**
     * Use order mode
     * @return OrderArguments
     */
    public final OrderArguments toOrderMode() {
        return this.orderMode;
    }
}