import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class CasinoLogic {
    private int onARoll = 1;
    private String username;
    private int amountMoney;
    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;
    public CasinoLogic(String givenUsername) {
        String csvFile = "./data.csv";  // Path to your CSV file
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";"); 
                if (givenUsername.equals(values[0])){
                    this.username = givenUsername;
                    this.amountMoney = Integer.parseInt(values[2]);
                    this.gamesPlayed = Integer.parseInt(values[3]);
                    this.gamesWon = Integer.parseInt(values[4]);
                    this.gamesLost = Integer.parseInt(values[5]);
                    System.out.println(amountMoney);
                }
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

    public String[] generateReels(int amountReels) {
        Random random = new Random();
        int[] randomNumbers = new int[amountReels];
        for (int i = 0; i < amountReels; i++) {
            randomNumbers[i] = random.nextInt(3);
        }

        String[] options = {"🍒", "🍋", "🍉", "🍊", "🍇", "🍓", "🥝", "🍍", "🍌", "🍏"};

        String [] givenReels = new String[amountReels];
        for (int i = 0; i < amountReels; i++) {
            givenReels[i] = options[randomNumbers[i]];
        }
        return givenReels;
    }

    public void spin(int betSize, boolean [] winningRows,boolean [] winningColumns) {
        int moneyWinMultiplier = 0;
        int countinedRoll = 0;
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
        }
        if (countinedRoll == 0){
            onARoll = 1;
        }
        this.amountMoney = this.amountMoney - betSize + betSize *moneyWinMultiplier;
        if (this.amountMoney < 0){
            this.amountMoney = 1;
        }
    }
}