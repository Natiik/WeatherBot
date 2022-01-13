import org.example.weatherBot.bot.SQL;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SQLTest {

    @Test
    void updateTest() {
        SQL test = new SQL();
        assertDoesNotThrow(() -> test.update("408419270","metrics","hgfhghgg"));
    }
}