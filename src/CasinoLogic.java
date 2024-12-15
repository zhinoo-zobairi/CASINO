import java.util.Random;

public class CasinoLogic {
    private int amountMoney;
    public CasinoLogic(int initialAmount) {
        this.amountMoney = initialAmount;
    }

    public int getAmountMoney() {
        return amountMoney;
    }

    public String[] generateReels() {
        Random random = new Random();
        int randomNum1 = random.nextInt(3);
        int randomNum2 = random.nextInt(3); 
        int randomNum3 = random.nextInt(3);  

        String[] options = {"🍉","🍋","🍒"};

        String [] givenReels = {options[randomNum1],options[randomNum2],options[randomNum3]};
        return givenReels;
    }

    public String spin(int betAmount, String[] generatedReels) {
        if (betAmount <= 0 || betAmount > amountMoney) {
            return "Invalid bet!";
        }
        if (generatedReels[0].equals(generatedReels[1]) && generatedReels[1].equals(generatedReels[2])) {
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