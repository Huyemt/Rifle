package org.rifle.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * @author Huyemt
 */

public class Utils {
    /**
     * 随机生成指定长度(length >= 2)的ID (不以0开头)
     *
     * Randomly generate an ID of a specified length (length >= 2 && not starting with 0)
     *
     * @param length
     * @param useLetter
     * @return String
     */
    public static String randomID(int length, boolean useLetter) {
        if (length <= 1)
            return "";

        Random random = new Random();
        StringBuilder result = new StringBuilder();
        int num;

        if (useLetter) {
            char[] letters = new char[26];
            for (int i = 0; i <= 25; i++)
                letters[i] = (char)(97 + i);

            while (result.length() < length) {
                if (random.nextInt(2) == 0) {
                    num = random.nextInt(10);
                    if (result.length() == 0 && num == 0)
                        continue;

                    result.append(num);
                } else
                    result.append(letters[random.nextInt(letters.length)]);
            }
        } else {
            while (result.length() < length) {
                num = random.nextInt(10);
                if (result.length() == 0 && num == 0)
                    continue;

                result.append(num);
            }
        }

        return result.toString();
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
