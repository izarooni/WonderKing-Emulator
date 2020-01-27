package com.izarooni.wkem.packet.codec;

import com.izarooni.wkem.packet.accessor.PacketWriter;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * @author izarooni
 */
public class GameEncoder extends ProtocolEncoderAdapter {

    @Override
    public void encode(IoSession session, Object o, ProtocolEncoderOutput out) throws Exception {
        PacketWriter w = (PacketWriter) o;
        byte[] data = w.getPacket();
        int pLength = data.length;
        byte[] send = new byte[pLength + 2];

        // header contains 2-bytes in big-endian?
        send[0] = (byte) (pLength & 0xff);
        send[1] = (byte) ((pLength >> 8) & 0xff);

        System.arraycopy(data, 0, send, 2, pLength);
        out.write(IoBuffer.wrap(send));
    }
}
