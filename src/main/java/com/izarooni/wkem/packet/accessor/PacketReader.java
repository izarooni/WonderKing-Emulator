package com.izarooni.wkem.packet.accessor;

/**
 * @author izarooni
 */
public class PacketReader extends LittleEndianReader {

    public PacketReader(byte[] arr) {
        super(arr);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public byte readByte() {
        return (byte) super.read();
    }

    public long readUnsignedInt() {
        return readInt() & 0xFFFFFFFFL;
    }
}
