import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CasinoLoginUITest {

    @Test
    void testLoginUser_valid() {
        // Setze voraus, dass "bartlew" in data.csv existiert
        // und das Passwort "123" korrekt ist.
        String[] usernames = CasinoLogic.getAllUsernames();
        String[] passwords = CasinoLogic.getAllPasswords();

        boolean result = callLoginUser("bartlew", "1234");
        assertTrue(result, "Sollte true für gültige Daten sein");
    }

    @Test
    void testLoginUser_invalid() {
        boolean result = callLoginUser("bartlew", "falsch");
        // Laut aktuellem Code wird true zurückgegeben, wenn Username stimmt,
        // aber Passwort nicht stimmt. Das ist ein Bug.
        // Wir erwarten eigentlich false.
        assertFalse(result, "Sollte false sein, wenn Passwort falsch ist (Bug!)");
    }

    @Test
    void testCheckPlayerExists() {
        // Vorausgesetzt, "Bob" existiert
        boolean exists = CasinoLoginUI.checkPlayerExists("bartlew", "irgendwas");
        assertTrue(exists, "Bob sollte existieren");
    }

    // Kopiert die Logik aus loginUser in eine Hilfsmethode oder nutze CasinoLoginUI.loginUser direkt
    private boolean callLoginUser(String username, String password) {
        return CasinoLoginUI.loginUser(username, password);
    }
}