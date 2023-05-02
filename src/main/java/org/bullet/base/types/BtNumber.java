package org.bullet.base.types;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Bullet的 Number 类型
 * <br><br>
 * The Bullet Number type
 *
 * @author Huyemt
 */

public class BtNumber extends BtType {
    public static final Pattern NUMBER_PARTTERN = Pattern.compile("^-?\\d+(\\.\\d+)?$");

    private enum BeLong {
        POSITIVE((byte) 1),
        ZERO((byte) 0),
        NEGATIVE((byte) -1);

        final byte id;

        BeLong(byte id) {
            this.id = id;
        }

        public BeLong negate() {
            return this == ZERO ? ZERO : this == POSITIVE ? NEGATIVE : POSITIVE;
        }
    }

    private BigDecimal bigDecimal;
    private char[] left;
    private char[] right;
    private BeLong beLong;

    public BtNumber() {
        left = new char[0];
        right = new char[0];
        beLong = BeLong.ZERO;
    }

    public BtNumber(int v) {
        this(String.valueOf(v));
    }

    public BtNumber(BigDecimal bigDecimal) {
        this(bigDecimal.toPlainString());
    }

    public BtNumber(String factor) {
        if (factor.length() == 0) {
            left = new char[0];
            right = new char[0];
            beLong = BeLong.ZERO;
        } else if (NUMBER_PARTTERN.matcher(factor).matches()) {
            char[] expr = factor.toCharArray();

            ArrayList<Character> a = new ArrayList<>();
            ArrayList<Character> b = new ArrayList<>();

            BeLong first = init(expr, a, b);

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

            this.left = left;
            this.right = right;

            this.beLong = left.length == 0 && right.length == 0 ? BeLong.ZERO : first;

            StringBuilder builder = new StringBuilder();

            if (beLong == BeLong.NEGATIVE) {
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

            bigDecimal = new BigDecimal(builder.toString());
        } else {
            System.out.println("error");
        }
    }

    public BtNumber add(BtNumber number) {
        return new BtNumber(bigDecimal.add(number.bigDecimal));
    }

    public BtNumber subtract(BtNumber number) {
        return new BtNumber(bigDecimal.subtract(number.bigDecimal));
    }

    public BtNumber multiply(BtNumber number) {
        return new BtNumber(bigDecimal.multiply(number.bigDecimal));
    }

    public BtNumber divide(BtNumber number) {
        return new BtNumber(bigDecimal.divide(number.bigDecimal, 16, RoundingMode.HALF_EVEN));
    }

    public BtNumber mod(BtNumber number) {
        return new BtNumber(bigDecimal.remainder(number.bigDecimal));
    }

    public BtNumber pow(int n) {
        return new BtNumber(bigDecimal.pow(n));
    }

    public BtNumber negate() {
        return new BtNumber(bigDecimal.negate());
    }

    public int compare(BtNumber number) {
        return bigDecimal.compareTo(number.bigDecimal);
    }

    public int toInteger() {
        return bigDecimal.toBigInteger().intValue();
    }

    public int toIntegerE() {
        return bigDecimal.intValueExact();
    }

    public BtNumber clone() {
        BtNumber number = new BtNumber();
        number.bigDecimal = bigDecimal;
        number.left = left.clone();
        number.right = right.clone();

        return number;
    }



    @Override
    public String toString() {
        return bigDecimal.toPlainString();
    }

    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////

    private String parseString(char[] chars) {
        if (chars.length == 0) {
            return "0";
        }

        StringBuilder builder = new StringBuilder();

        for (char c : chars)
            builder.insert(0, c);

        return builder.toString();
    }

    private BeLong init(char[] expr, ArrayList<Character> a, ArrayList<Character> b) {
        int i = 0;
        boolean floating = false;
        BeLong first = expr[i] == '-' ? BeLong.NEGATIVE : BeLong.POSITIVE;

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
