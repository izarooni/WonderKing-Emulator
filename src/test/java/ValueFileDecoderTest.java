import com.izarooni.wkem.io.DataReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ValueFileDecoderTest {

    public static void main(String[] args) throws Exception {
        File file = new File("value");
        // disable so maven doesn't re-decode .dat files??
//        decryptFiles(file.listFiles());

//        decryptFile(new File("value/baseitemdata.dat"));
//        System.out.println("complete");
    }

    private static void decryptFiles(File[] files) throws IOException {
        for (File file : files) {
            if (file.isDirectory()) {
                decryptFiles(file.listFiles());
                continue;
            }
            decryptFile(file);
            System.out.printf("Updated file '%s'\r\n", file.toString());
        }
    }

    private static void decryptFile(File file) throws IOException {
        byte[] buf;
        try (FileInputStream in = DataReader.openFile(file)) {
            buf = in.readAllBytes();
            DataReader.magic_xor(buf, buf.length);
        }
        try (FileOutputStream out = new FileOutputStream(file)) {
            out.write(buf);
            out.flush();
        }
    }
}
