import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.codec.AES;

/**
 * @author izarooni
 */
public class PacketDecodeTest {

    public static void main(String[] args) {
        AES.expandKey();
        //   len   |                         packet header                                      |                                    packet body
        // [26, 0, | 61, 0, 92, -5, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, | 69, 96, -64, 16, -128, -121, 57, 60, 52, -3, 73, -28, -2, 44, 124, -119, -115, -88]
        // output: "izarooni : teU." supposed to be "izarooni : test"

        byte[] rawPacket = new byte[]{26, 0, 61, 0, 92, -5, 1, 0, 69, 96, -64, 16, -128, -121, 57, 60, 52, -3, 73, -28, -2, 44, 124, -119, -115, -88};
        byte[] packetBody = new byte[rawPacket.length - 8];

        System.arraycopy(rawPacket, 8, packetBody, 0, packetBody.length);
        AES.decrypt(packetBody);

        System.out.printf("raw          : %s\n", new EndianReader(rawPacket).toString());
        System.out.printf("decoded body : %s\n", new EndianReader(packetBody).toString());
        System.out.printf("Cp1252 body  : %s\n", new EndianReader(packetBody).toAsciiString());
    }
}
