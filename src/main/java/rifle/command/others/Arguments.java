package rifle.command.others;

/**
 * @author Huyemt
 */

public abstract class Arguments {
    protected final String origin;

    public Arguments(String arguments) {
        this.origin = arguments.replace("  ", " ");
    }

    public String getOrigin() {
        return origin;
    }
}
