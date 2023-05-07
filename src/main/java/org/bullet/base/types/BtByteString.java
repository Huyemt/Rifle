package org.bullet.base.types;

import org.bullet.exceptions.BulletException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * @author Huyemt
 */

public class BtByteString extends BtType {
    private final ArrayList<BtByte> bytes = new ArrayList<>();

    public BtByteString(String value, boolean done) throws BulletException {
        if (done) {
            if (value.length() % 4 == 0) {
                int index = 0;

                while (index != value.length()) {
                    bytes.add(new BtByte(value.substring(index, index+4)));

                    index += 4;
                }
            }

            return;
        }

        for (byte b : value.getBytes()) {
            this.bytes.add(new BtByte(b));
        }
    }

    public BtByteString(String value) throws BulletException {
        this(value, false);
    }


    public BtByteString(byte[] bytes) {
        for (byte b : bytes) {
            this.bytes.add(new BtByte(b));
        }
    }

    public BtByte get(int index) {
        return bytes.get(index);
    }

    public int size() {
        return bytes.size();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("b'");

        for (BtByte btByte : bytes) {
            builder.append(btByte.toString().toLowerCase());
        }

        builder.append("'");

        return builder.toString();
    }

    public final String translate(String encoding) throws UnsupportedEncodingException {
        byte[] r = new byte[bytes.size()];

        for (int i = 0; i < bytes.size(); i++) {
            r[i] = bytes.get(i).getValue();
        }

        return new String(r, encoding);
    }

    public byte[] toByteArray() {
        byte[] r = new byte[bytes.size()];

        for (int i = 0; i < bytes.size(); i++) {
            r[i] = bytes.get(i).getValue();
        }

        return r;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o instanceof BtByteString) {
            BtByteString byteString = (BtByteString) o;

            if (size() != byteString.size()) return false;

            for (int i = 0; i < size(); i++) {
                if (get(i).getValue() == byteString.get(i).getValue()) continue;
                return false;
            }

            return true;
        }

        return false;
    }
}
