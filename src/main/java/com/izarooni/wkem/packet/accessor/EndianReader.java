package com.izarooni.wkem.packet.accessor;

import com.izarooni.wkem.util.ByteArray;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @author izarooni
 */
public class EndianReader {

    private static final String CodePage = "Windows-1252";
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
        return new String(buf, Charset.forName(CodePage));
    }

    /**
     * @return copy of the buffer
     */
    public byte[] array() {
        return Arrays.copyOf(buf, buf.length);
    }

    /**
     * @return signed byte (int8)
     */
    public byte readByte() {
        return (byte) readByte(index++);
    }

    public int readByte(int i) {
        return (((int) buf[i]) & UByte);
    }

    /**
     * @return signed short (int16)
     */
    public short readShort() {
        return (short) (readByte(index++) + (readByte(index++) << 8));
    }

    /**
     * @return signed int (int32)
     */
    public int readInt() {
        return readInt(index);
    }

    public int readInt(int i) {
        int ret = 0;
        for (int b = 0; b < Integer.BYTES; b++) {
            ret += readByte(i + b) << (b * 8);
            index++;
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
        return Integer.toUnsignedLong(
                (readByte(index))
                        + (readByte(index + 1) << 8L)
                        + (readByte(index + 2) << 16L)
                        + (readByte(index + 3) << 24L));
    }

    //region these don't belong but are necessary for the AES shit; will make sense of it's belonging another time
    public void writeByte(int index, byte value) {
        buf[index] = value;
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
    //endregion

    /**
     * @return signed long (int64)
     */
    public long readLong() {
        return (readByte(index))
                + (readByte(index + 1) << 8L)
                + (readByte(index + 2) << 16L)
                + (readByte(index + 3) << 24L)
                + (((long) readByte(index + 4)) << 32L)
                + (((long) readByte(index + 5)) << 40L)
                + (((long) readByte(index + 6)) << 48L)
                + (((long) readByte(index + 7)) << 56L);
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
        return new String(read(n), Charset.forName(CodePage));
    }
}
