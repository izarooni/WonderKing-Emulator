package com.izarooni.wkem.packet.codec;

import com.izarooni.wkem.packet.accessor.PacketReader;
import com.izarooni.wkem.util.ByteArray;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author izarooni
 */
public class LoginDecoder extends ProtocolDecoderAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginDecoder.class);
    private static final String SessionAttribute = String.format("%s.%s", LoginDecoder.class.getName(), "state");

    @Override
    public void decode(IoSession session, IoBuffer buffer, ProtocolDecoderOutput out) throws Exception {
        DecoderState state = (DecoderState) session.getAttribute(SessionAttribute);

        if (state != null) {
            // append to previous buffer and we're done! let the server process the packet now
            session.removeAttribute(SessionAttribute);
            byte[] remaining = new byte[state.packet.length - state.offset];
            buffer.get(remaining, 0, remaining.length);
            decode(remaining);
            System.arraycopy(remaining, 0, state.packet, state.offset, remaining.length);
            out.write(new PacketReader(state.packet));
            state.packet = null;
            return;
        }

        // read the length of the incoming packet
        byte[] lengthData = new byte[2];
        buffer.get(lengthData, 0, 2);
        int packetLength = ByteArray.toUnsignedShort(lengthData);

        // array of the entire packet minus the 2 bytes we read to determine the packet length
        byte[] packet = new byte[packetLength - 2];
        byte[] data = new byte[packet.length - 6];
        buffer.get(packet, 0, 6); // populate packet header
        buffer.get(data, 0, buffer.remaining()); // populate actual packet data
        decode(data);
        // copy decoded packet data into the packet buffer
        System.arraycopy(data, 0, packet, 6, data.length);

        // insufficient packet data
        if (packetLength > buffer.limit()) {
            // store necessary data for the next buffer
            state = new DecoderState();
            state.offset = buffer.limit() - 2; // packet length offset
            state.packet = packet;
            session.setAttribute(SessionAttribute, state);
        } else {
            // sufficient amount of packet data; let server process
            out.write(new PacketReader(packet));
        }
    }

    private static void decode(byte[] b) {
        for (int i = 0; i < b.length; ++i) {
            b[i] = (byte) (b[i] ^ (i ^ 3 * (254 - i)));
        }
    }
}
