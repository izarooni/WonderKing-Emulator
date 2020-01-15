package com.izarooni.wkem.packet.codec;

import com.izarooni.wkem.packet.accessor.PacketWriter;
import com.izarooni.wkem.util.ByteArray;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * @author izarooni
 */
public class LoginEncoder extends ProtocolEncoderAdapter {

    @Override
    public void encode(IoSession session, Object o, ProtocolEncoderOutput out) throws Exception {
        PacketWriter w = (PacketWriter) o;
        byte[] data = w.getPacket();
        byte[] packet = new byte[w.length() + 6];
        if (packet.length > 0x7FFF) { // not entirely sure if we can encode an unsigned amount of bytes in Java
            throw new RuntimeException("packet buffer is too long (" + packet.length + ")");
        }
        byte[] dataLength = ByteArray.getBytesInt16((short) packet.length);
        // insert data that says the packet length
        System.arraycopy(dataLength, 0, packet, 0, dataLength.length);
        // insert packet operation code with a 2-byte offset:
        System.arraycopy(data, 0, packet, 2, 2);
        // insert packet data with an 8-byte offset:
        // [packet length (2 bytes)] [operation code (2 bytes)] [etc data (6 bytes)] [packet body (remaining bytes)]
        System.arraycopy(data, 2, packet, 8, data.length - 2);
        out.write(IoBuffer.wrap(packet));
    }
}
