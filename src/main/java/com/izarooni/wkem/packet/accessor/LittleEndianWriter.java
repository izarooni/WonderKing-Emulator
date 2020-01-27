package com.izarooni.wkem.packet.accessor;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author izarooni
 */
public class LittleEndianWriter {

    private static final Charset ASCII = StandardCharsets.US_ASCII;
    ByteArrayOutputStream baos;

    LittleEndianWriter(int size) {
        baos = new ByteArrayOutputStream(size);
    }

    public void write(byte[] b) {
        for (int x : b) {
            baos.write(x);
        }
    }

    public void write(int b) {
        baos.write((byte) b);
    }

    public void nCopies(byte value, int copies) {
        for (int i = 0; i < copies; i++) {
            baos.write(value);
        }
    }

    public void skip(int b) {
        write(new byte[b]);
    }

    public void writeShort(int i) {
        baos.write((byte) (i & 0xFF));
        baos.write((byte) ((i >>> 8) & 0xFF));
    }

    public void writeInt(int i) {
        baos.write((byte) (i & 0xFF));
        baos.write((byte) ((i >>> 8) & 0xFF));
        baos.write((byte) ((i >>> 16) & 0xFF));
        baos.write((byte) ((i >>> 24) & 0xFF));
    }

    public void writeAsciiString(String s) {
        if (s == null) {
            throw new NullPointerException("Can't write a null string to the byte array");
        }
        write(s.getBytes(ASCII));
    }

    public void writeAsciiString(String s, int length) {
        if (s.length() > length) {
            throw new IllegalArgumentException(String.format("name cannot be longer than %d chars", length));
        }
        writeAsciiString(s);
        for (int i = s.length(); i < length; i++) {
            write(0);
        }
    }

    public void writeLong(long l) {
        baos.write((byte) (l & 0xFF));
        baos.write((byte) ((l >>> 8) & 0xFF));
        baos.write((byte) ((l >>> 16) & 0xFF));
        baos.write((byte) ((l >>> 24) & 0xFF));
        baos.write((byte) ((l >>> 32) & 0xFF));
        baos.write((byte) ((l >>> 40) & 0xFF));
        baos.write((byte) ((l >>> 48) & 0xFF));
        baos.write((byte) ((l >>> 56) & 0xFF));
    }
}