import com.izarooni.wkem.io.DataReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ValueFileDecoderTest {

    public static void main(String[] args) throws Exception {
        File file = new File("value/npcdataH.dat");
//        decryptFile(file, true);
//        decryptFiles(file.listFiles());
    }

    private static void decryptFiles(File[] files) throws IOException {
        if (files == null) return;
        for (File file : files) {
            if (file.isDirectory()) {
                decryptFiles(file.listFiles());
            } else if (file.getName().endsWith(".dat")) {
                decryptFile(file ,false);
            }
        }
    }

    private static void decryptFile(File file, boolean force) throws IOException {
        byte[] buf;
        try (FileInputStream in = DataReader.openFile(file)) {
            buf = in.readAllBytes();
            if (!force && !needsDecode(buf)) {
                return;
            }
            DataReader.magic_xor(buf, buf.length);
        }
        try (FileOutputStream out = new FileOutputStream(file)) {
            out.write(buf);
            out.flush();
        }
        System.out.printf("Updated file '%s'\r\n", file.toString());
    }

    private static boolean needsDecode(byte[] buf) {
        long magic = 0;
        for (byte b : buf) {
            if ((b & 0xff) == 0xc5) magic++;
        }
        return (magic / (float) buf.length) > 0.3;
    }
}
