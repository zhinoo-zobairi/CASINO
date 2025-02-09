import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CasinoLogicBlackboxTest {

    private CasinoLogic logic;

    @Before
    public void setUp() {
        // Für die Tests setzen wir den Modus beispielsweise auf 1 (Früchte-Symbole).
        logic = new CasinoLogic("bartlew", 1);
    }

    // EC1: amountReels < 0 → NegativeArraySizeException wird erwartet.
    @Test(expected = NegativeArraySizeException.class)
    public void testGenerateReelsNegative() {
        logic.generateReels(-5);
    }

    // EC2: amountReels == 0 → Leeres Array
    @Test
    public void testGenerateReelsZero() {
        String[] reels = logic.generateReels(0);
        assertNotNull(reels);
        assertEquals(0, reels.length);
    }

    // EC3: amountReels > 0 → Array der richtigen Länge und mit gültigen Symbolen
    @Test
    public void testGenerateReelsPositive() {
        int amountReels = 10;
        String[] reels = logic.generateReels(amountReels);
        assertNotNull(reels);
        assertEquals(amountReels, reels.length);

        // Für Modus 1 erwarten wir, dass die Symbole zu diesem Set gehören:
        String[] validSymbols = {"🍒", "🍋", "🍉", "🍊", "🍇", "🍓", "🥝", "🍍", "🍌", "🍏"};
        for (String symbol : reels) {
            boolean found = false;
            for (String valid : validSymbols) {
                if (symbol.equals(valid)) {
                    found = true;
                    break;
                }
            }
            assertTrue("Symbol " + symbol + " ist nicht gültig.", found);
        }
    }
}