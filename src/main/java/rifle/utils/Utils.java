package rifle.utils;

/**
 * @author Huyemt
 */

public class Utils {

    /**
     * Split an command into names and arguments
     * @param command
     * @return names and arguments
     */
    public static String[] spiltCommand(String command) {
        String[] result = new String[]{"", ""};

        if (command.length() > 0) {
            if (command.contains(" ")) {
                /*
                "test -lang java" will be [test, -lang java]
                 */
                result[0] = command.substring(0, command.indexOf(' '));
                result[1] = command.substring(command.indexOf(' ') + 1);
            } else
                result[0] = command;
        }
        return result;
    }

    /**
     * Extract the name of a command argument item
     * @param anArgument
     * @return argument key name
     */
    public static String extractKeyName(String anArgument) {
        if (anArgument == null || anArgument.length() == 0 || anArgument.charAt(0) != '-')
            return null;

        int leftIndex = -1;
        for (char argument_char : anArgument.toCharArray()) {
            leftIndex += 1;
            if (argument_char != '-')
                break;
        }

        return anArgument.substring(leftIndex).toLowerCase();
    }
}
