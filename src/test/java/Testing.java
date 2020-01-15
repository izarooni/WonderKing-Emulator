import com.izarooni.wkem.io.DataReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Testing {

    private static final Logger LOGGER = LoggerFactory.getLogger(Testing.class);

    public static void main(String[] args) throws IOException {
        DataReader.createCache();
        DataReader.getBaseItemData().values().stream().filter(i -> i.descriptionc.contains("GM")).forEach(i -> System.out.println(i));
    }
}
