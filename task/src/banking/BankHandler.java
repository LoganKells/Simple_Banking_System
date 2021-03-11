package banking;

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

            // Get their input
            try {
                this.userSelection = userInput.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Error. Please enter an integer selection 0-3");
                userInput.next(); // Clear input stream
                getUserSelection();
            }

            switch (userSelection) {
                case 1:
                    int loc = -1;
                    do {
                        CreditCard c = createCreditCard();
                        loc = cardNums.indexOf(c.getCardNumber());
                    } while (loc > -1);

                case 2:

            }

        } while (this.userSelection != 0);
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
