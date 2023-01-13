package rifle.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;

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
                "test -lang java" will be string-array [test, -lang java]
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

    public static String readFile(File file) throws IOException {
        if (!file.exists() || file.isDirectory())
            throw new FileNotFoundException();
        return readFile(new FileInputStream(file));
    }

    public static String readFile(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists() || file.isDirectory())
            throw new FileNotFoundException();
        return readFile(new FileInputStream(file));
    }

    public static String readFile(InputStream inputStream) throws IOException {
        return readFile(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    /**
     * Read a file
     * @param reader
     * @return String
     * @throws IOException
     */
    private static String readFile(Reader reader) throws IOException {
        try (BufferedReader br = new BufferedReader(reader)) {
            String temp;
            StringBuilder stringBuilder = new StringBuilder();
            temp = br.readLine();
            while (temp != null) {
                if (stringBuilder.length() != 0)
                    stringBuilder.append("\n");
                stringBuilder.append(temp);
                temp = br.readLine();
            }
            return stringBuilder.toString();
        }
    }
}
