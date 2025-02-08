import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CasinoLogic {
    private int rowInCsv;
    private int onARoll = 1;
    private String username;
    private int amountMoney;
    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;
    private int currentMode;

    /**
     * Konstruktor: Lädt die Daten des Spielers (Username, Guthaben, Spiele usw.) aus der CSV-Datei.
     * @param givenUsername Übergebener Benutzername
     * @param mode Der zu Beginn eingestellte Spielmodus
     */
    public CasinoLogic(String givenUsername, int mode) {
        String csvFile = "./data.csv";  
        String line;
        int rowCount = 0;
        currentMode = mode;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";"); 
                if (givenUsername.equals(values[0])){
                    rowInCsv = rowCount;
                    this.username = givenUsername;
                    this.amountMoney = Integer.parseInt(values[2]);
                    this.gamesPlayed = Integer.parseInt(values[3]);
                    this.gamesWon = Integer.parseInt(values[4]);
                }
                rowCount++;
            }
        } catch (IOException e) {
            new Exception("Error: Data hasnt loaded properly!");
        }
    }

    /**
     * Gibt das aktuelle Guthaben des Spielers zurück.
     */
    public int getAmountMoney() {
        return amountMoney;
    }

    /**
     * Gibt den aktuellen Spielmodus zurück (1, 2 oder 3).
     */
    public int getCurrentMode(){
        return currentMode;
    }

    /**
     * Gibt den aktuellen On-A-Roll-Multiplikator zurück (für bestimmte Bonusgewinne).
     */
    public int getOnARoll (){
        return onARoll; // On-A-Roll-Multiplikator gibt Bonus bei aufeinanderfolgenden Gewinnen
    }

    /**
     * Aktualisiert die Daten des Spielers in der CSV-Datei (nur möglich über CasinoLogic).
     * @param rowIndex Index in der CSV-Datei
     * @param financialData Array mit neuen Werten für Guthaben, Spiele, Gewinne etc.
     */
    public void updateData(int rowIndex, String[] financialData) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("./data.csv"));
        ArrayList<String> data = new ArrayList<>();
        if (rowIndex >= 0 && rowIndex < lines.size()) {
            String[] columns = lines.get(rowIndex).split(";");
            data.add(columns[0]);
            data.add(columns[1]);
            data.addAll(Arrays.asList(financialData));
            lines.set(rowIndex, String.join(";", data.toArray(new String[0])));
        } else {
            return;
        }

        try (FileWriter writer = new FileWriter("./data.csv")) { 
            for (String line : lines) {
                writer.write(line + "\n");
            }
        }
    }

    /**
     * Hängt einen neuen Datensatz am Ende der CSV-Datei an (bei Registrierung).
     * @param data Daten-Array (Username, Passwort, Geld, Spiele, Gewinne ...)
     */
    public static void appendData(String[] data) throws IOException{
        try (FileWriter writer = new FileWriter("./data.csv", true)) { 
            writer.write(String.join(";", data));
            writer.write("\n");
        }
    }

    /**
     * Setzt das Geld eines Spielers in der CSV-Datei zurück auf 10000.
     * Zusätzlich werden Spiele und Gewinne auf 0 gesetzt.
     * @param username Benutzername, dessen Werte zurückgesetzt werden sollen
     */
    public static void resetPlayerMoney(String username) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("./data.csv"));
        try (FileWriter writer = new FileWriter("./data.csv")) {
            for (String line : lines) {
                String[] parts = line.split(";"); 
                if (parts[0].equals(username)) {
                    writer.write(username + ";" + parts[1] + ";" + 10000 + ";" + 0 + ";" + 0 + ";"+ "\n");
                } else {
                    writer.write(line + "\n"); 
                }
            }
        }
    }

    /**
     * Liefert alle Benutzernamen aus der CSV-Datei als String-Array zurück.
     */
    public static String[] getAllUsernames (){
        ArrayList<String> usernames = new ArrayList<>();
        String csvFile = "./data.csv";
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";"); 
                usernames.add(values[0]);
            }
        } catch (IOException e) {
            new Exception("Error: Data hasnt loaded properly!");
        }
        return usernames.stream()
                .map(String::valueOf)
                .toArray(String[]::new);
    }

    /**
     * Liefert alle Passwörter aus der CSV-Datei als String-Array zurück.
     */
    public static String[] getAllPasswords (){
        ArrayList<String> passwords = new ArrayList<>();
        String csvFile = "./data.csv";
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";"); 
                passwords.add(values[1]);
            }
        } catch (IOException e) {
            new Exception("Error: Data hasnt loaded properly!");
        }
        System.out.println("");
        return passwords.stream()
                .map(String::valueOf)
                .toArray(String[]::new);
    }

    /**
     * Ändert den Spielmodus in der Reihenfolge 1 -> 2 -> 3 -> wieder 1.
     */
    public void changeMode (){
        if (currentMode == 1){
            currentMode = 2;
        }
        else if (currentMode == 2){
            currentMode = 3;
        }
        else if (currentMode == 3){
            currentMode = 1;
        }
    }

    /**
     * Generiert ein Array von Symbolen (z.B. Früchte, Herzen, Emojis), abhängig vom aktuellen Modus.
     * @param amountReels Anzahl der angeforderten Symbole
     * @return Ein Array von Symbolen als String
     */
    public String[] generateReels(int amountReels) {
        Random random = new Random();
        int[] randomNumbers = new int[amountReels];
        int reelCount= 3;

        if (getCurrentMode()== 1){
            reelCount = 3;
        }
        else if (getCurrentMode()== 2) {
            reelCount = 2;
        }
        else if (getCurrentMode()== 3) {
            reelCount = 3;
        }
        for (int i = 0; i < amountReels; i++) {
            randomNumbers[i] = random.nextInt(reelCount);
        }

        // Legt die verschiedenen Symbol-Sets je nach Modus fest
        String[] options = {"🍒", "🍋", "🍉", "🍊", "🍇", "🍓", "🥝", "🍍", "🍌", "🍏"};
        if (currentMode == 1) {
            options = new String[]{"🍒", "🍋", "🍉", "🍊", "🍇", "🍓", "🥝", "🍍", "🍌", "🍏"};
        }
        else if (currentMode == 2){
            options = new String[]{ "💖", "💙", "💚", "💛", "🧡", "🖤", "🤍", "🤎", "💜", "💝"};
        }
        else if (currentMode == 3) {
            options = new String[]{"😊", "😎", "😂", "😍", "😡", "😭", "🤔", "🥳", "😴", "😱"};
        }

        String [] givenReels = new String[amountReels];
        for (int i = 0; i < amountReels; i++) {
            givenReels[i] = options[randomNumbers[i]];
        }
        return givenReels;
    }

    /**
     * Verrechnet einen Dreh (Spin) im Spiel:
     * - Erhöht die gespielten Spiele
     * - Ermittelt aus Reihen-/Spaltensiegen oder einem Smiley (Modus 2) den Gewinnmultiplikator
     * - Aktualisiert das Guthaben des Spielers und speichert es in der CSV-Datei
     * @param betSize Einsatz
     * @param winningRows Array aus boolschen Werten, welche Reihen gewonnen haben
     * @param winningColumns Array aus boolschen Werten, welche Spalten gewonnen haben
     * @param smileyActive Ob ein Smiley-Muster gefunden wurde (nur Modus 2)
     */
    public void spin(int betSize, boolean [] winningRows, boolean [] winningColumns, boolean smileyActive) throws IOException {
        int moneyWinMultiplier = 0;
        int countinedRoll = 0;
        gamesPlayed++;

        // Modus 1 oder 3: Prüfe, ob Reihen oder Spalten gewonnen haben
        if (getCurrentMode() == 1 || getCurrentMode() == 3 ){
            for (int i = 0; i < 5; i++) {
                int encounteredLineInRow = 0;
                int encounteredLineInColumn = 0;
                if (winningRows[i] == true){
                    moneyWinMultiplier = moneyWinMultiplier + 5;
                    encounteredLineInRow++;
                }
                if (winningColumns[i] == true){
                    moneyWinMultiplier = moneyWinMultiplier + 5;
                    encounteredLineInColumn++;
                }
                // Wenn sowohl Reihe als auch Spalte in derselben Zeile gewonnen, gibt es zusätzlichen Bonus
                if (encounteredLineInColumn != 0 && encounteredLineInRow != 0){
                    moneyWinMultiplier =+ 11 * onARoll;
                    onARoll =+ 10;
                    countinedRoll = 1;
                }
                if (winningRows[i] == true || winningColumns[i] == true){
                    gamesWon++;
                }
            }
        }

        // Modus 2: Prüfe, ob Smiley erreicht wurde
        if (getCurrentMode() == 2 && smileyActive){
            moneyWinMultiplier = 50;
            onARoll = 100;
        }

        if (countinedRoll == 0){
            onARoll = 1;
        }

        // Berechnet neues Guthaben
        this.amountMoney = this.amountMoney - betSize + betSize * moneyWinMultiplier;
        if (this.amountMoney < 0){
            this.amountMoney = 1;
        }

        // Speichere aktualisierte Daten in CSV
        String[] data = {String.valueOf(getAmountMoney()), String.valueOf(gamesPlayed), String.valueOf(gamesWon)};
        updateData(rowInCsv, data);
    }
}