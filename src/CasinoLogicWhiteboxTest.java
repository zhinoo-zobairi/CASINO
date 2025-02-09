import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;

public class CasinoLogicWhiteboxTest {

    private CasinoLogic logic;

    @Before
    public void setUp() {
        logic = new CasinoLogic("bartlew", 1); 

    }

    @Test
    public void testSpinMode1NoWin() throws IOException {
        // Testfall: Modus 1, ohne Gewinn – alle win-Felder false
        boolean[] winningRows = {false, false, false, false, false};
        boolean[] winningColumns = {false, false, false, false, false};
        int betSize = 100;

        logic.spin(betSize, winningRows, winningColumns, false);

        // Erwartet: Kein Gewinn, also amountMoney = 10000 - 100 = 9900.
        assertEquals(9900, logic.getAmountMoney());
    }

    @Test
    public void testSpinMode1RowWin() throws IOException {
        // Testfall: Modus 1, ein Gewinn in einer Zeile
        boolean[] winningRows = {true, false, false, false, false};
        boolean[] winningColumns = {false, false, false, false, false};
        int betSize = 100;

        logic.spin(betSize, winningRows, winningColumns, false);

        // Berechnung: Für einen Zeilengewinn wird moneyWinMultiplier um 5 erhöht.
        // Erwartet: amountMoney = 10000 - 100 + 100 * 5 = 10400.
        assertEquals(10400, logic.getAmountMoney());
    }

    @Test
    public void testSpinMode2SmileyWin() throws IOException {
        // Testfall: Modus 2, Smiley-Muster aktiviert
        // Dafür initialisieren wir den Logic in Modus 2:
        logic = new CasinoLogic("testUser", 2);
        int betSize = 100;

        // Für Modus 2 werden winningRows und winningColumns nicht ausgewertet
        logic.spin(betSize, null, null, true);

        // Erwartet: moneyWinMultiplier wird auf 50 gesetzt → amountMoney = 10000 - 100 + 100*50 = 14900.
        assertEquals(14900, logic.getAmountMoney());
    }


}
