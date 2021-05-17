package banking;

import java.util.Arrays;
import java.util.Random;

public class CreditCard {
    private long cardNumber;
    private int pin;
    private double balance;

    // Constructor
    CreditCard() {
        this.cardNumber = generateLuhnCardNumber(4_000_000_000_000_000L, 10.0);
        this.pin = generatePin();
        this.balance = 0.0;
    }

    // Method to generate a random card number
    // Card number format: 400000DDDDDDDDDD where D is a random digit. For example 4000075375637831
    private long generateRandomCardNumber(long bin, double accountLength) {
        Random randomGen = new Random();
        double exp = 9.0;
        long num = bin;
        for (int i = 0; i < (int) accountLength; i++) {
            int currDigit = randomGen.nextInt(9);
            long currNum = (long) (currDigit * Math.pow(10.0, exp));
            num += currNum;
            exp -= 1;
        }
        return num;
    }

    // Method to generate a Luhn algorithm compliant card number
    // see https://hyperskill.org/projects/93/stages/516/preview
    private long generateLuhnCardNumber(long bin, double accountLength) {
        // Step 1 & 2) Generate a card number where the account sequence of 10 digits ends in zero.
        // Example: 4000007062053260
        long randomCard = generateRandomCardNumber(bin, accountLength);

        // Convert the card number to an array for easy indexing
        Long num = (Long) randomCard;
        String s = num.toString();
        String[] digits = s.split("");
        int[] integers = new int[16];
        integers = Arrays.stream(digits)
                .mapToInt(Integer::parseInt)
                .toArray();
        // Save the original card numbers for use in end.
        int[] cardNumber = Arrays.stream(integers).toArray();

        // Step 2) Drop the last digit to zero
        integers[15] = 0;

        // Step 3) Multiply odd indexes by 2 (where indexing starts with 1 to 16)
        for (int i = 0; i < 16; i += 2) {
            int n = integers[i] * 2;
            integers[i] = n;
        }

        // Step 4) Subtract 9 to numbers over 9
        int[] newInt = new int[16];
        newInt = Arrays.stream(integers)
                .map(val -> val > 9 ? val - 9 : val)
                .toArray();

        // Step 5) Calculate the control number by adding all numbers
        int controlNum = Arrays.stream(newInt).sum();

        // Determine the last digit that ensures the random account number satisfies the Luhn algorithm. The modulus = (control number + checkSum) / 10 should be zero.
        int checkSum = controlNum % 10 == 0
                ? 0
                : (10 - (controlNum % 10));

        // Return the card number that now adheres to the Luhn algorithm
        cardNumber[15] = checkSum;
        StringBuilder cardNumStringBuilder = new StringBuilder();
        Arrays.stream(cardNumber).forEach(cardNumStringBuilder::append);
        String cardNumString = cardNumStringBuilder.toString();
        return Long.parseLong(cardNumString);
    }

    // Generate PIN from 1111 to 9999.
    private int generatePin() {
        Random randomGen = new Random();
        int newPin = 0;
        double exp = 3.0;
        for (int i = 0; i < 4; i++) {
            int r = Math.max(randomGen.nextInt(9), 1);
            int nextDigit = (int) Math.pow(10.0, exp);
            //System.out.println("r: " + r + ", dig: " + nextDigit);
            newPin += r * nextDigit;
            exp -= 1;
        }
        return newPin;
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
