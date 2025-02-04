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
    public CasinoLogic(String givenUsername, int mode) {
        String csvFile = "./data.csv";  // Path to your CSV file
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

    public int getAmountMoney() {
        return amountMoney;
    }
    public int getOnARoll (){
        return onARoll; //On a roll multiplayer doesnt give any advantage if 0, but when 2 lines then it increases the next winning by 10
    }


    // CasinoLogic kann als einzige Klasse die csv-datei beeinflussen. Die Anderungen konnen nur von
    // Casino-frame gemacht werden (ausgeschlossen ist die resetPlayerMoney() function, die fur Profil-frame bestimmt ist)
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

    public static void appendData(String[] data) throws IOException{
        try (FileWriter writer = new FileWriter("./data.csv", true)) { 
            writer.write(String.join(";", data));
            writer.write("\n");
            }
    }

    
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
    public static String[] getAllUsernames (){
        ArrayList<String> usernames = new ArrayList<>();
        String csvFile = "./data.csv";  // Path to your CSV file
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

    public static String[] getAllPasswords (){
        ArrayList<String> passwords = new ArrayList<>();
        String csvFile = "./data.csv";  // Path to your CSV file
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

    public void changeMode (){
        if (currentMode == 1){
            currentMode = 2;
        }
        else{
            currentMode = 1;
        }
    }


    public String[] generateReels(int amountReels) {
        Random random = new Random();
        int[] randomNumbers = new int[amountReels];
        for (int i = 0; i < amountReels; i++) {
            randomNumbers[i] = random.nextInt(3);
        }
        String[] options = {"🍒", "🍋", "🍉", "🍊", "🍇", "🍓", "🥝", "🍍", "🍌", "🍏"};
        if (currentMode == 1) {
            options = new String[]{"🍒", "🍋", "🍉", "🍊", "🍇", "🍓", "🥝", "🍍", "🍌", "🍏"};
        }
        else if (currentMode == 2){
            options = new String[]{ "💖", "💙", "💚", "💛", "🧡", "🖤", "🤍", "🤎", "💜", "💝"};
        }
        

        String [] givenReels = new String[amountReels];
        for (int i = 0; i < amountReels; i++) {
            givenReels[i] = options[randomNumbers[i]];
        }
        return givenReels;
    }

    public void spin(int betSize, boolean [] winningRows,boolean [] winningColumns) throws IOException {
        int moneyWinMultiplier = 0;
        int countinedRoll = 0;
        gamesPlayed++;
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
            if (encounteredLineInColumn != 0 && encounteredLineInRow != 0){
                moneyWinMultiplier =+ 11 * onARoll;
                onARoll =+ 10;
                countinedRoll = 1;
            }
            if (winningRows[i] == true || winningColumns[i] == true){
                gamesWon++;
            }
        }
        if (countinedRoll == 0){
            onARoll = 1;
        }
        this.amountMoney = this.amountMoney - betSize + betSize *moneyWinMultiplier;
        if (this.amountMoney < 0){
            this.amountMoney = 1;
        }
        String[] data = {String.valueOf(getAmountMoney()), String.valueOf(gamesPlayed), String.valueOf(gamesWon)};
        updateData(rowInCsv, data);
    }
}