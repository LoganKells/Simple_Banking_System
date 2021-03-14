package banking;

import java.sql.SQLOutput;
import java.util.*;

class BankHandler {
    private Scanner userInput;
    private int userSelection;
    ArrayList<CreditCard> cardOperator = new ArrayList<>();
    ArrayList<Long> cardNums = new ArrayList<Long>();

    // Constructor
    BankHandler() {
        this.userInput = new Scanner(System.in);
        this.userSelection = -1;
    }

    // Method to process user's selection
    public void getUserSelection() {
        // Loop while user selection != 0
        do {
            // Print the options
            System.out.println("1. Create an account");
            System.out.println("2. Log into account");
            System.out.println("0. Exit");
            // Get user's selection
            try {
                this.userSelection = userInput.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Error. Please enter an integer selection 0-3");
                userInput.next(); // Clear input stream
                getUserSelection();
            }
            // Handle user user's selection.
            switch (userSelection) {
                case 1: // Create a credit card
                    int loc = -1;
                    do {
                        CreditCard c = createCreditCard();
                        loc = cardNums.indexOf(c.getCardNumber());
                    } while (loc > -1);
                    break;
                case 2: // Log into an account
                    int operatorIndex = login();
                    // Open the credit account.
                    if (operatorIndex >= 0) {
                        CreditCard userCard = cardOperator.get(operatorIndex);
                        AccountOperator(userCard);
                    }
                    break;
                default: // Exit the program
                    break;
            }
        } while (this.userSelection != 0);
    }

    // Try to log the user into an account.
    public int login() {
        // Prompt the user for their credit card login information
        System.out.println("Enter your card number:");
        long userCreditNumber = userInput.nextLong();
        System.out.println("Enter your PIN:");
        int userPin = userInput.nextInt();

        // Determine if the credentials are correct.
        int databaseIndex = cardNums.indexOf(userCreditNumber);
        if (databaseIndex >= 0) {
            // See if the pin is correct.
            CreditCard userCard = cardOperator.get(databaseIndex);
            if (userCard.checkPin(userPin)) {
                System.out.println("You have successfully logged in!");
            } else {
                System.out.println("Wrong card number or PIN!");
            }
        }
        return databaseIndex;
    }

    // Account Management Operator
    private void AccountOperator(CreditCard c) {
        int userSelect = -1;
        do {
            // Print options
            System.out.println("1. Balance");
            System.out.println("2. Log out");
            System.out.println("0. Exit");
            // get user's selection
            try {
                userSelect = userInput.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Error. Please enter an integer selection 0-3");
                userInput.next(); // Clear input stream
                AccountOperator(c);
            }
            // Handle the user's selection
            switch (userSelect) {
                case 1: // Check Balance
                    double balance = c.getBalance();
                    System.out.println("Balance: " + balance);
                    break;
                case 2: // Logout
                    System.out.println("You have successfully logged out!");
                    getUserSelection();
                    break;
                default: // Exit program
                    break;
            }
        } while(userSelect != 0 && userSelect != 2);
    }

    // Create credit cards on demand
    private CreditCard createCreditCard() {
        CreditCard c = new CreditCard();

        // Add the credit card to the system.
        cardNums.add(c.getCardNumber());
        cardOperator.add(c);

        // Print the card details for the user
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(c.getCardNumber());
        System.out.println("Your card PIN:");
        System.out.println(c.getPin());
        return new CreditCard();
    }
}
