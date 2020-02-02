import com.izarooni.wkem.io.DataReader;
import com.izarooni.wkem.io.MapFactory;
import com.izarooni.wkem.io.meta.TemplateMap;
import com.izarooni.wkem.io.meta.TemplateSpawnPoint;

import java.io.IOException;

/**
 * @author izarooni
 */
public class DatFileCacheTest {

    public static void main(String[] args) throws IOException {
        DataReader.createCache();

        System.out.println("Search for map...");
        TemplateMap map = MapFactory.get(303);
        System.out.println(map);
        for (TemplateSpawnPoint sp : map.spawnPoints) {
            System.out.println(sp);
        }
    }
}
