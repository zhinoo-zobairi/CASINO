import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;

public class CasinoLogicTest {

    private CasinoLogic logic;

    @BeforeEach
    void setup() {
        logic = new CasinoLogic("TestUser", 1);
    }

    @Test
    void testInitialMode() {
        assertEquals(1, logic.getCurrentMode(), "Modus sollte beim Start 1 sein");
    }

    @Test
    void testChangeMode() {
        logic.changeMode();
        assertEquals(2, logic.getCurrentMode(), "Nach einem Wechsel sollte Modus = 2 sein");
        logic.changeMode();
        assertEquals(3, logic.getCurrentMode(), "Nach zweitem Wechsel sollte Modus = 3 sein");
        logic.changeMode();
        assertEquals(1, logic.getCurrentMode(), "Nach drittem Wechsel wieder 1");
    }

    @Test
    void testSpinWinRowsAndCols() throws IOException {
        // Beispiel: 5er Feld, in dem die erste Zeile und erste Spalte gewonnen haben
        boolean[] winningRows = { true, false, false, false, false };
        boolean[] winningCols = { true, false, false, false, false };
        int oldMoney = logic.getAmountMoney();

        logic.spin(100, winningRows, winningCols, false);
        int newMoney = logic.getAmountMoney();

        // Geld sollte gestiegen sein (oder zumindest nicht so stark sinken)
        assertTrue(newMoney > oldMoney - 100, "Gewinn oder nur kleiner Verlust erwartet");
    }

    @Test
    void testGenerateReels() {
        String[] reels = logic.generateReels(25);
        assertEquals(25, reels.length, "Muss 25 Symbole liefern");
        for (String symbol : reels) {
            assertNotNull(symbol, "Kein Symbol darf null sein");
        }
    }
}