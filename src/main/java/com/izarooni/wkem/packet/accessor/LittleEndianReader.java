package com.izarooni.wkem.packet.accessor;

import com.izarooni.wkem.util.ByteArray;

/**
 * @author izarooni
 */
public class LittleEndianReader {

    private int pos = 0; // the read index of the byte array
    private final byte[] arr; // the array of byte content

    public LittleEndianReader(byte[] arr) {
        this.arr = arr;
    }

    @Override
    public String toString() {
        return ByteArray.toHex(arr);
    }

    int read() {
        return (((int) arr[pos++]) & 0xFF);
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

    public long readLong() {
        long ret = 0;
        for (int i = 0; i < 8; i++) {
            ret += (long) read() << (i * 8);
        }
        return ret;
    }

    public void skip(int num) {
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
        char[] ret = new char[n & 0xFF];
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
