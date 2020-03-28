package com.izarooni.wkem.packet.codec;

import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.util.ByteArray;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * @author izarooni
 */
public class GameDecoder extends ProtocolDecoderAdapter {

    private static final String SessionAttribute = String.format("%s.%s", LoginDecoder.class.getName(), "state");

    @Override
    public void decode(IoSession session, IoBuffer buffer, ProtocolDecoderOutput out) throws Exception {
        DecoderState state = (DecoderState) session.getAttribute(SessionAttribute);

        // read the length of the incoming packet
        byte[] lengthData = new byte[2];
        buffer.get(lengthData, 0, 2);
        int packetLength = ByteArray.toUnsignedShort(lengthData) - 2;

        if (buffer.limit() < packetLength) {

        }

        byte[] packet = new byte[packetLength];
        buffer.get(packet, 0, 6);

        byte[] decode = new byte[packet.length - 6];
        buffer.get(decode, 0, decode.length);
        AES.decrypt(decode);
        System.arraycopy(decode, 0, packet, 6, decode.length);
        out.write(new EndianReader(packet));
    }
}
