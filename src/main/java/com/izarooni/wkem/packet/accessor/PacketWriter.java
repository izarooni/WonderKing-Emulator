package com.izarooni.wkem.packet.accessor;

import com.izarooni.wkem.util.ByteArray;

/**
 * @author izarooni
 */
public class PacketWriter extends LittleEndianWriter {

    public PacketWriter() {
        this(32);
    }

    public PacketWriter(int size) {
        super(size);
    }

    public void writeBoolean(boolean b) {
        baos.write(b ? 1 : 0);
    }

    public byte[] getPacket() {
        return baos.toByteArray();
    }

    public int length() {
        return baos.size();
    }

    @Override
    public String toString() {
        return ByteArray.toHex(getPacket());
    }
}
