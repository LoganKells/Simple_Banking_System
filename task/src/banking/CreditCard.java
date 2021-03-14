package banking;

import java.util.Random;

public class CreditCard {
    private long cardNumber;
    private int pin;
    private double balance;

    // Constructor
    CreditCard() {
        generateCardNumber();
        generatePin();
        this.balance = 0.0;
    }

    // Method to generate the card number
    private void generateCardNumber() {
        Random randomGen = new Random();
        double exp = 10.0;
        long num = 4_000_000_000_000_000L;
        for (int i = 0; i < 11; i++) {
            int currDigit = randomGen.nextInt(9);
            long currNum = (long) (currDigit * Math.pow(10.0, exp));
            num += currNum;
            exp -= 1;
        }
        this.cardNumber = num;
    }

    // Generate PIN from 0000 to 9999.
    private void generatePin() {
        Random randomGen = new Random();
        this.pin = randomGen.nextInt(9999);
    }

    // Accessors ------------------------------------
    public int getPin() {
        return this.pin;
    }

    public long getCardNumber() {
        return this.cardNumber;
    }

    public double getBalance() {
        return this.balance;
    }



    // Other ------------------------------------
    // Check if a given PIN matches the card's PIN
    public boolean checkPin(int testPin) {
        return this.pin == testPin;
    }


}
