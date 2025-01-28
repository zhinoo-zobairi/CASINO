import java.util.Random;

public class CasinoLogic {
    private int amountMoney;
    private int onARoll = 1;
    public CasinoLogic(int initialAmount) {
        this.amountMoney = initialAmount;
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
    }
}