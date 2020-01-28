package com.izarooni.wkem.packet.accessor;

import com.izarooni.wkem.util.ByteArray;

import java.util.Arrays;

/**
 * @author izarooni
 */
public class LittleEndianAccessor {

    private static final short UBYTE = 0xFF;

    private int pos = 0; // the read index of the byte array
    private byte[] arr; // the array of byte content

    public LittleEndianAccessor(byte[] arr) {
        this.arr = arr;
    }

    @Override
    public String toString() {
        return toString(true);
    }

    public String toString(boolean hex) {
        return hex ? ByteArray.toHex(arr) : Arrays.toString(arr);
    }

    public String toAsciiString() {
        char[] ret = new char[arr.length];
        for (int x = 0; x < arr.length; x++) {
            ret[x] = (char) readAt(x);
        }
        return String.valueOf(ret);
    }

    public byte[] array() {
        return Arrays.copyOf(arr, arr.length);
    }

    int read() {
        return (((int) arr[pos++]) & UBYTE);
    }

    private int readAt(int n) {
        return (((int) arr[n]) & 0xFF);
    }

    public short readShort() {
        int b1 = read();
        int b2 = read() << 8;
        return (short) (b1 + b2);
    }

    public int readInt() {
        int ret = 0;
        for (int i = 0; i < 4; i++) {
            ret += read() << (i * 8);
        }
        return ret;
    }

    public long readUnsignedInt() {
        return Integer.toUnsignedLong(readInt());
    }

    public long readUnsignedInt(int index) {
        int b1, b2, b3, b4;
        b1 = (arr[index] & UBYTE);
        b2 = (arr[index + 1] & UBYTE) << 8L;
        b3 = (arr[index + 2] & UBYTE) << 16L;
        b4 = (arr[index + 3] & UBYTE) << 24L;
        return Integer.toUnsignedLong(b1 + b2 + b3 + b4);
    }

    public void putInt(int index, int value) {
        arr[index] = ((byte) (value & UBYTE));
        arr[index + 1] = ((byte) ((value >>> 8) & UBYTE));
        arr[index + 2] = ((byte) ((value >>> 16) & UBYTE));
        arr[index + 3] = ((byte) ((value >>> 24) & UBYTE));
    }

    public long readLong() {
        long ret = 0;
        for (int i = 0; i < 8; i++) {
            ret += (long) read() << (i * 8);
        }
        return ret;
    }

    public void skip(int num) {
        if (num + pos > arr.length) {
            throw new IndexOutOfBoundsException(String.format("cannot skip %d bytes, %d remain", num, (arr.length - pos)));
        }
        for (int i = 0; i < num; i++) {
            read();
        }
    }

    public void seek(int num) {
        if (num < 0 || num > arr.length) {
            throw new IllegalArgumentException("cannot seek position beyond packet length or below 0");
        }
        pos = num;
    }

    public byte[] read(int num) {
        byte[] ret = new byte[num];
        for (int i = 0; i < num; i++) {
            ret[i] = (byte) read();
        }
        return ret;
    }

    public char readChar() {
        return (char) read();
    }

    public String readAsciiString(int n) {
        char[] ret = new char[n & UBYTE];
        for (int x = 0; x < n; x++) {
            ret[x] = readChar();
        }
        return String.valueOf(ret);
    }

    public int getPosition() {
        return pos;
    }

    public int available() {
        return arr.length - pos;
    }

    public int length() {
        return arr.length;
    }
}
