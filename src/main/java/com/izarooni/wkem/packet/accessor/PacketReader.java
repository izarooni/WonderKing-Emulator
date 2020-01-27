package com.izarooni.wkem.packet.accessor;

/**
 * @author izarooni
 */
@Deprecated
public class PacketReader extends LittleEndianAccessor {

    public PacketReader(byte[] arr) {
        super(arr);
    }

    public byte readByte() {
        return (byte) super.read();
    }
}
