package com.izarooni.wkem.util;

public class ByteArray {

    private ByteArray() {
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static byte[] getBytesInt16(short i) {
        byte[] b = new byte[2];
        b[0] = (byte) (i & 0xFF);
        b[1] = (byte) ((i >>> 8) & 0xFF);
        return b;
    }

    /**
     * Reads an array sequentially and sums them as unsigned bytes.
     * <p>
     * Also known as '{@code int16}'
     * </p>
     */
    public static int toShort(byte[] bytes) {
        if (bytes == null) {
            throw new NullPointerException("array cannot be null");
        } else if (bytes.length != 2) {
            throw new IllegalArgumentException("array is too short or too long (" + bytes.length + ")");
        }
        return (bytes[0] & 0xFF) + ((bytes[1] & 0xFF) << 8);
    }

    /**
     * Reads an array of bytes (decimals) and returns them as a concatenated String of hexadecimals separated by spaces.
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