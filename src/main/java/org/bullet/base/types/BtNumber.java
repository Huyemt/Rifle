package org.bullet.base.types;

import org.bullet.exceptions.BulletException;

import java.util.ArrayList;

/**
 * Bullet的 Number 类型
 * <br><br>
 *
 * @author Huyemt
 */

public class BtNumber {
    public final ArrayList<Integer> left;
    public final ArrayList<Integer> right;
    public boolean positive;

    public BtNumber(String value) throws BulletException {
        left = new ArrayList<>();
        right = new ArrayList<>();
        positive = true;

        this.translate(value);
    }

    private void translate(String value) throws BulletException {
        boolean flag = false;

        for (char c : value.toCharArray()) {
            if (Character.isDigit(c) || c == '.') {
                if (c == '.') {
                    if (!flag) {
                        flag = true;
                        continue;
                    }

                    throw new BulletException("Illegal number");
                }

                if (flag)
                    right.add(0, Character.getNumericValue(c));
                else
                    left.add(0, Character.getNumericValue(c));
                continue;
            }

            throw new BulletException("Illegal number");
        }
    }

    @Override
    public String toString() {
        if (right.size() == 0 && left.size() == 0) {
            return "0";
        }

        StringBuilder builder = new StringBuilder();



        return super.toString();
    }

    public static void main(String[] args) throws BulletException {
        BtNumber BtNumber = new BtNumber("1.1");
    }
}
