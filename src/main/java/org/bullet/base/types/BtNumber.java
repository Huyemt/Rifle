package org.bullet.base.types;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        if (NUMBER_PARTTERN.matcher(factor).matches())
            value = new BigDecimal(format(factor));
        else value = null;

        under = null;
    }

    public BtNumber add(BtNumber number) {
        if (value == null) return number;
        if (number == null) return this;

        return new BtNumber(exact().add(number.exact()));
    }

    public BtNumber subtract(BtNumber number) {
        if (value == null) return number.negate();
        if (number == null) return this;

        return new BtNumber(exact().subtract(number.exact()));
    }

    public BtNumber multiply(BtNumber number) {
        if (value == null || number == null) return new BtNumber(0);

        String a = toString();
        String b = number.toString();
        BtNumber number1;

        if (under != null && under.toString().equals(b)) {
            number1 = clone();
            number1.under = null;
        } else if (number.under != null && number.under.toString().equals(a)) {
            number1 = number.clone();
            number1.under = null;
        } else number1 = new BtNumber(exact().multiply(number.exact()));

        return number1;

    }

    public BtNumber divide(BtNumber number) {
        if (value == null) return new BtNumber(0);
        BtNumber number1;

        if (under == null)
            number1 = clone();
        else {
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
        return format(exact().toPlainString());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o instanceof BtNumber && compare((BtNumber) o) == 0;
    }

    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////

    private String format(String expr) {
        char[] exprs = expr.toCharArray();
        int iIndex = -1;
        int fIndex = -1;
        int point = -1;

        for (int i = 0; i < exprs.length; i++) {
            if (exprs[i] == '.') {
                point = i;
                continue;
            }

            if (exprs[i] == '0') {
                if (point > -1) {
                    if (exprs[i - 1] == '0' || exprs[i - 1] == '.') continue;

                    fIndex = i;
                } else {
                    if (i + 1 == exprs.length) {
                        if (iIndex == -1) iIndex = i;

                        continue;
                    }

                    if (i + 1 < exprs.length && exprs[i + 1] == '.') continue;

                    if (iIndex + 1 == i && iIndex > -1 && exprs[iIndex] == '0') iIndex = i;
                }
            } else {
                if (point > -1) fIndex = i + 1;
                else if (iIndex == -1) iIndex = i;
            }
        }

        if (iIndex == -1) {
            if (point > -1) return fIndex > -1 ? expr.substring(0, fIndex) : expr.substring(0, point);
            return "0";
        } else {
            if (point > -1) return fIndex > -1 ? expr.substring(iIndex, fIndex) : expr.substring(iIndex, point);
            return expr.substring(iIndex);
        }
    }
}
