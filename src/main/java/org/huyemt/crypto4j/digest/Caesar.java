package org.huyemt.crypto4j.digest;

import java.io.CharArrayWriter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Huyemt
 */

public class Caesar {
    public Caesar() {

    }

    public final String encrypt(char[] content, Key key) {
        CharArrayWriter result = new CharArrayWriter();

        if (key instanceof OffsetKey) {
            OffsetKey key1 = ((OffsetKey) key);
            for (char aChar : content) {
                result.append((char)((((int) aChar)) + key1.getOffset()));
            }
        }

        if (key instanceof ReplaceKey) {
            ReplaceKey key1 = ((ReplaceKey) key);
            for (char aChar : content) {
                result.append(key1.get(aChar));
            }
        }

        return result.toString();
    }

    public final String encrypt(char[] content) {
        return encrypt(content, new OffsetKey());
    }

    public final String encrypt(String content, Key key) {
        return encrypt(content.toCharArray(), key);
    }

    public final String encrypt(String content) {
        return encrypt(content.toCharArray(), new OffsetKey());
    }

    public final String decrypt(char[] content, Key key) {
        CharArrayWriter result = new CharArrayWriter();

        if (key instanceof OffsetKey) {
            OffsetKey key1 = ((OffsetKey) key);
            for (char aChar : content) {
                result.append((char)((((int) aChar)) + (-key1.getOffset())));
            }
        }

        if (key instanceof ReplaceKey) {
            ReplaceKey key1 = ((ReplaceKey) key);
            for (char aChar : content) {
                result.append(key1.getOrigin(aChar));
            }
        }

        return result.toString();
    }

    public final String decrypt(char[] content) {
        return decrypt(content, new OffsetKey());
    }

    public final String decrypt(String content, Key key) {
        return decrypt(content.toCharArray(), key);
    }

    public final String decrypt(String content) {
        return decrypt(content.toCharArray(), new OffsetKey());
    }

    /**
     * 密钥的父类抽象
     * The superclass abstraction of the key
     */
    public static abstract class Key {
        public abstract String formatIt();

        @Override
        public final String toString() {
            return formatIt();
        }
    }

    public static class OffsetKey extends Key {
        private int offset;
        public boolean forwardR = true;

        public OffsetKey(int offset) {
            if (offset > 0) {
                this.offset = offset;
            }
        }

        public final OffsetKey setOffset(int offset) {
            this.offset = forwardR ? Math.abs(offset) : (offset > 0 ? -offset : offset);
            return this;
        }

        public OffsetKey() {
            this(10);
        }

        public final int getOffset() {
            return forwardR ? Math.abs(offset) : (offset > 0 ? -offset : offset);
        }

        @Override
        public final String formatIt() {
            return String.valueOf(getOffset());
        }
    }

    public static class ReplaceKey extends Key {
        private final LinkedHashMap<Character, Character> replaces;

        public ReplaceKey() {
            replaces = new LinkedHashMap<>();
        }

        public final ReplaceKey add(Character origin, Character replace) {
            replaces.put(origin, replace);
            return this;
        }

        public final ReplaceKey remove(Character origin) {
            replaces.remove(origin);
            return this;
        }

        public final ReplaceKey set(Character origin, Character replace) {
            if (contains(origin) && !containsValue(replace)) {
                replaces.put(origin, replace);
            }

            return this;
        }

        public final Character get(Character origin) {
            return replaces.getOrDefault(origin, origin);
        }

        public final Character getOrigin(Character replace) {
            if (containsValue(replace)) {
                for (Map.Entry<Character, Character> entry : replaces.entrySet()) {
                    if (replace.equals(entry.getValue())) {
                        return entry.getKey();
                    }
                }
            }

            return replace;
        }

        public final boolean contains(Character origin) {
            return replaces.containsKey(origin);
        }

        public final boolean containsValue(Character replace) {
            return replaces.containsValue(replace);
        }

        public final LinkedHashMap<Character, String> getReplaces() {
            return (LinkedHashMap<Character, String>) replaces.clone();
        }

        @Override
        public final String formatIt() {
            return replaces.toString();
        }
    }
}
