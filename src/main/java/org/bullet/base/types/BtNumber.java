package org.bullet.base.types;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * @author Huyemt
 */

public class BtNumber extends BtType {
    public static final Pattern NUMBER_PARTTERN = Pattern.compile("^-?\\d+(\\.\\d+)?$");

    private BigDecimal value;
    private BtNumber under;

    public BtNumber() {
        under = null;
        value = null;
    }

    public BtNumber(int v) {
        under = null;
        value = new BigDecimal(v);
    }

    public BtNumber(BigDecimal molecule) {
        this(molecule.toPlainString());
    }

    public BtNumber(String factor) {
        if (NUMBER_PARTTERN.matcher(factor).matches()) {
            value = new BigDecimal(formatNumber(factor.toCharArray()));
        } else {
            value = null;
        }

        under = null;
    }

    public BtNumber add(BtNumber number) {
        if (value == null) {
            return number;
        }

        if (number == null) {
            return this;
        }

        return new BtNumber(exact().add(number.exact()));
    }

    public BtNumber subtract(BtNumber number) {
        if (value == null) {
            return number.negate();
        }

        if (number == null) {
            return this;
        }

        return new BtNumber(exact().subtract(number.exact()));
    }

    public BtNumber multiply(BtNumber number) {
        if (value == null || number == null) {
            return new BtNumber(0);
        }

        String a = toString();
        String b = number.toString();
        BtNumber number1;

        if (under != null && under.toString().equals(b)) {
            number1 = clone();
            number1.under = null;
        } else if (number.under != null && number.under.toString().equals(a)) {
            number1 = number.clone();
            number1.under = null;
        } else {
            number1 = new BtNumber(exact().multiply(number.exact()));
        }

        return number1;

    }

    public BtNumber divide(BtNumber number) {
        if (value == null) {
            return new BtNumber(0);
        }
        BtNumber number1;

        if (under == null) {
            number1 = clone();
        } else {
            number1 = new BtNumber();
            number1.value = this.value;
        }

        number1.under = number;

        return number1;
    }

    public BtNumber mod(BtNumber number) {
        return new BtNumber(exact().remainder(number.exact()));
    }

    public BtNumber pow(int n) {
        return new BtNumber(exact().pow(n));
    }

    public BtNumber negate() {
        return new BtNumber(exact().negate());
    }

    public int compare(BtNumber number) {
        return exact().compareTo(number.exact());
    }

    public int toInteger() {
        return exact().intValue();
    }

    public int toIntegerE() {
        return exact().intValueExact();
    }

    public BtNumber clone() {
        BtNumber number = new BtNumber();
        number.value = value;
        number.under = under;

        return number;
    }

    @Override
    public String toString() {
        return formatNumber(exact().toPlainString().toCharArray());
    }

    private BigDecimal exact() {
        BtNumber now = under;
        BigDecimal temp = value;

        while (now != null) {
            temp = temp.divide(now.value, 20, RoundingMode.HALF_EVEN);
            now = now.under;
        }

        return temp;
    }

    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////

    private String formatNumber(char[] expr) {
        ArrayList<Character> a = new ArrayList<>();
        ArrayList<Character> b = new ArrayList<>();

        int first = init(expr, a, b);

        int i = positionZero(a, true);

        char[] left = new char[i == -1 ? a.size() : i];

        for (int j = 0; j < left.length; j++) {
            left[j] = a.get(j);
        }

        i = positionZero(b, false);

        char[] right = new char[i == -1 ? b.size() : b.size() - i - 1];

        for (int oIndex = i + 1, n = 0; oIndex < b.size(); oIndex++, n++) {
            right[n] = b.get(oIndex);
        }

        StringBuilder builder = new StringBuilder();

        if ((left.length == 0 && right.length == 0 ? 0 : first) == -1) {
            builder.append("-");
        }

        if (left.length == 0) {
            builder.append(0);
        } else {
            builder.append(parseString(left));
        }

        if (right.length > 0) {
            builder.append(".").append(parseString(right));
        }

        return builder.toString();
    }

    private String parseString(char[] chars) {
        if (chars.length == 0) {
            return "0";
        }

        StringBuilder builder = new StringBuilder();

        for (char c : chars)
            builder.insert(0, c);

        return builder.toString();
    }

    private int init(char[] expr, ArrayList<Character> a, ArrayList<Character> b) {
        int i = 0;
        boolean floating = false;
        int first = expr[i] == '-' ? -1 : 1;

        if (expr[i] == '+' || expr[i] == '-') {
            i++;
        }

        for (; i < expr.length; i++) {
            if (expr[i] == '.') {
                floating = true;
                continue;
            }

            if (floating) {
                b.add(0, expr[i]);
            } else {
                a.add(0, expr[i]);
            }
        }

        return first;
    }

    private int positionZero(ArrayList<Character> list, boolean flag) {
        int i = -1;

        if (flag) {
            int a = list.size() - 1;

            for (int j = a; j >= 0; j--) {
                if (list.get(j) != '0') {
                    if (j == a) {
                        break;
                    }
                } else {
                    if (j < a) {
                        if (list.get(j + 1) == '0') {
                            i = j;
                            continue;
                        }

                        break;
                    }
                }
            }
        } else {
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j) == '0') {
                    if (j > 0 && list.get(j - 1) != '0') {
                        continue;
                    }

                    i = j;
                    continue;
                }

                break;
            }
        }

        return i;
    }
}
