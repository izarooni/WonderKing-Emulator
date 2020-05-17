import com.izarooni.wkem.client.User;
import com.izarooni.wkem.io.DataReader;
import com.izarooni.wkem.life.Player;
import com.izarooni.wkem.life.meta.storage.Item;
import com.izarooni.wkem.life.meta.storage.Storage;
import com.izarooni.wkem.life.meta.storage.StorageType;
import com.izarooni.wkem.packet.magic.LoginPacketCreator;
import com.izarooni.wkem.util.Trunk;
import org.apache.mina.core.session.DummySession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author izarooni
 */
public class UserSaveTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserSaveTest.class);

    public static void main(String[] args) throws Exception {
        DataReader.createCache();

        try (Connection con = Trunk.getConnection()) {
            Trunk.migrate(con);

            User user = new User(new DummySession());
            if (user.login("username", "password") != LoginPacketCreator.LoginResponse_Ok) {
                LOGGER.info("Incorrect credentials.");
                return;
            }
            LOGGER.info("Logged-in user '{}'", user.getUsername());

            Player player = new Player();
            player.setUsername("izarooni");

            Storage eq = player.getStorage().get(StorageType.Equipped);
            eq.equipItem(new Item((short) 767)); // (GM) uniform shoes
            eq.equipItem(new Item((short) 763)); // (GM) uniform hat

            try (PreparedStatement ps = con.prepareStatement("insert into players (account_id, username, hair, eyes, gender) values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                // minimum data to create a new row in the `player` table
                ps.setInt(1, user.getId());
                ps.setString(2, player.getUsername());
                ps.setShort(3, player.getHair());
                ps.setShort(4, player.getEyes());
                ps.setByte(5, player.getGender());

                if (ps.executeUpdate() == 1) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        // move cursor to first index and retrieve the generated value from players.id
                        rs.next();
                        player.setId(rs.getInt(1));
                    }
                    // this will save all other data (stats, level, map, equips, etc.)
                    player.save(con);
                }
            }
        }
    }
}
