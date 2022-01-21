import org.example.weatherBot.bot.DataBaseProperties;
import org.example.weatherBot.bot.SQL;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SQLTest {

    @Test
    void updateTest() {
        SQL test = new SQL(new DataBaseProperties());
        assertDoesNotThrow(() -> test.update("408419270","metrics","hgfhghgg"));
    }
}