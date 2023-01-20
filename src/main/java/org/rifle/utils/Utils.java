package org.rifle.utils;

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
}
