package com.izarooni.wkem.util;

public class ByteArray {

    private ByteArray() {
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static int[] asUnsigned(byte[] b) {
        int[] s = new int[b.length];
        for (int i = 0; i < b.length; i++) {
            s[i] = b[i] & 0xFF;
        }
        return s;
    }

    public static byte[] getBytesInt16(int i) {
        byte[] b = new byte[2];
        b[0] = (byte) (i & 0xFF);
        b[1] = (byte) ((i >>> 8) & 0xFF);
        return b;
    }

    /**
     * Reads bytes sequentially in little-endian format.
     */
    public static int toUnsignedShort(byte[] bytes) {
        if (bytes == null) {
            throw new NullPointerException("array cannot be null");
        } else if (bytes.length != 2) {
            throw new IllegalArgumentException("array is too short or too long (" + bytes.length + ")");
        }
        return (bytes[0] & 0xFF) + ((bytes[1] & 0xFF) << 8);
    }

    /**
     * Reads bytes sequentially in big-endian format
     */
    public static long toBEUnsignedInt(byte[] bytes) {
        if (bytes == null) {
            throw new NullPointerException("array cannot be null");
        } else if (bytes.length != 4) {
            throw new IllegalArgumentException("array is too short or too long (" + bytes.length + ")");
        }
        return Integer.toUnsignedLong((bytes[3] & 0xFF)
                + ((bytes[2] & 0xFF) << 8)
                + ((bytes[1] & 0xFF) << 16)
                + ((bytes[0] & 0xFF) << 24));
    }

    /**
     * Reads an array of bytes and returns them as a concatenated String of hexadecimals separated by spaces.
     */
    public static String toHex(byte[] bytes) {
        if (bytes == null) {
            throw new NullPointerException("array cannot be null");
        } else if (bytes.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            int v = b & 0xFF;
            sb
                    .append(HEX_ARRAY[v >>> 4])
                    .append(HEX_ARRAY[v & 0x0F])
                    .append(" ");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}