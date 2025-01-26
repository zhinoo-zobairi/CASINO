import java.util.Random;

public class CasinoLogic {
    private int amountMoney;
    public CasinoLogic(int initialAmount) {
        this.amountMoney = initialAmount;
    }

    public int getAmountMoney() {
        return amountMoney;
    }

    public String[] generateReels(int amountReels) {
        Random random = new Random();
        int[] randomNumbers = new int[amountReels];
        for (int i = 0; i < amountReels; i++) {
            randomNumbers[i] = random.nextInt(2);
        }

        String[] options = {"🍒", "🍋", "🍉", "🍊", "🍇", "🍓", "🥝", "🍍", "🍌", "🍏"};

        String [] givenReels = new String[amountReels];
        for (int i = 0; i < amountReels; i++) {
            givenReels[i] = options[randomNumbers[i]];
        }
        return givenReels;
    }

    public String spin(int betAmount, String[] generatedReels) {
        if (betAmount <= 0 || betAmount > amountMoney) {
            return "Invalid bet!";
        }
        if (generatedReels[0].equals(generatedReels[1]) && generatedReels[1].equals(generatedReels[2]) && generatedReels[2].equals(generatedReels[3]) 
        && generatedReels[3].equals(generatedReels[4])) {
            amountMoney += 5*betAmount;
            return "Your current wealth:\n" + amountMoney;
        } else {
            amountMoney -= betAmount;
            if (amountMoney <= 0) {
                amountMoney = 0;
                return "You lost! Current wealth: 0. You are out of money!";
            }
            return "Your current wealth:\n" + amountMoney;
        }
    }
}