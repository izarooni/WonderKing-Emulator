package com.izarooni.wkem.packet.accessor;

import com.izarooni.wkem.util.ByteArray;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author izarooni
 */
public class EndianReader {

    private static final short UByte = 0xFF;

    private int index = 0; // read index of the byte array
    private byte[] buf;

    public EndianReader(byte[] buf) {
        this.buf = buf;
    }

    public int getPosition() {
        return index;
    }

    public int available() {
        return buf.length - index;
    }

    public int length() {
        return buf.length;
    }

    @Override
    public String toString() {
        return ByteArray.toHex(buf);
    }

    public String toAsciiString() {
        return new String(buf, StandardCharsets.US_ASCII);
    }

    /**
     * @return copy of the buffer
     */
    public byte[] array() {
        return Arrays.copyOf(buf, buf.length);
    }

    /**
     * @return unsigned byte (int8)
     */
    int read() {
        return (((int) buf[index++]) & UByte);
    }

    /**
     * @return signed byte (int8)
     */
    public byte readByte() {
        return (byte) read();
    }

    /**
     * @return signed short (int16)
     */
    public short readShort() {
        return (short) (read() + (read() << 8));
    }

    /**
     * @return signed int (int32)
     */
    public int readInt() {
        int ret = 0;
        for (int i = 0; i < Integer.BYTES; i++) {
            ret += read() << (i * 8);
        }
        return ret;
    }

    /**
     * @return unsigned int (int32)
     */
    public long readUnsignedInt() {
        return Integer.toUnsignedLong(readInt());
    }

    /**
     * @param index position in the buffer to read from
     * @return unsigned int (int32)
     */
    public long readUnsignedInt(int index) {
        int b1, b2, b3, b4;
        b1 = (buf[index] & UByte);
        b2 = (buf[index + 1] & UByte) << 8L;
        b3 = (buf[index + 2] & UByte) << 16L;
        b4 = (buf[index + 3] & UByte) << 24L;
        return Integer.toUnsignedLong(b1 + b2 + b3 + b4);
    }

    /**
     * @param index position in the buffer to write to
     * @param value little-endian int (int32)
     */
    public void writeInt(int index, int value) {
        buf[index] = ((byte) (value & UByte));
        buf[index + 1] = ((byte) ((value >>> 8) & UByte));
        buf[index + 2] = ((byte) ((value >>> 16) & UByte));
        buf[index + 3] = ((byte) ((value >>> 24) & UByte));
    }

    /**
     * @return signed long (int64)
     */
    public long readLong() {
        long ret = 0;
        for (int i = 0; i < Long.BYTES; i++) {
            ret += ((long) read()) << (i * 8);
        }
        return ret;
    }

    /**
     * Moves the "cursor" position where certain method calls will start reading from in the buffer
     *
     * @param num index in the buffer
     */
    public void seek(int num) {
        if (num < 0 || num > buf.length) {
            throw new IllegalArgumentException("cannot seek position beyond packet length or below 0");
        }
        index = num;
    }

    public void skip(int num) {
        if (num + index < 0 || num > buf.length) {
            throw new IllegalArgumentException("cannot skip beyond packet length or below 0");
        }
        index += num;
    }

    /**
     * @param num amount of bytes to read
     * @return an array of bytes read from the buffer
     */
    public byte[] read(int num) {
        byte[] dest = new byte[num];
        System.arraycopy(buf, index, dest, 0, dest.length);
        index += num;
        return dest;
    }

    public String readAsciiString(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append((char) read());
        }
        return sb.toString();
    }
}
