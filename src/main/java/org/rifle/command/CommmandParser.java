package org.rifle.command;

/**
 * @author Huyemt
 */

public class CommmandParser {

    /**
     * 将整条指令拆分为两部分 -> 指令名称, 指令参数
     *
     * Split the whole command into two parts -> command name, command parameters
     *
     * @param commandLine
     * @return String[]
     */
    public static String[] splitCommand(String commandLine) {
        if (commandLine == null || commandLine.length() == 0)
            return new String[]{"", ""};

        if (!commandLine.contains(" "))
            return new String[]{commandLine, ""};

        int space_firstIndex = commandLine.indexOf(" ");
        return new String[]{commandLine.substring(0, space_firstIndex), commandLine.substring(space_firstIndex + 1).replace("  ", " ")};
    }

    /**
     * 提取键名, 用于KeyArgument的keys名称提取
     *
     * Extracting the key name (Keys name extraction for KeyArgument)
     *
     * @param key
     * @return String
     */
    public static String extractName(String key) {
        if (key == null || key.length() == 0)
            return "";

        if (!key.contains("-"))
            return key;

        char[] cs = key.toCharArray();
        int startIndex = 0;
        for (; startIndex < cs.length;startIndex++) {
            if (cs[startIndex] != '-')
                break;
        }

        return key.substring(startIndex);
    }
}
