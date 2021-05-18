package banking;

import java.util.*;

class BankController {
    private Scanner userInput;
    private int userSelection;
    private int table_id;
    ArrayList<CreditCard> cardOperator = new ArrayList<>();
    ArrayList<Long> cardNums = new ArrayList<Long>();
    DatabaseBuilder bank_db;

    // Constructor
    BankController() {
        this.userInput = new Scanner(System.in);
        this.userSelection = -1;

        // Create a new SQLite3 database if one does not already exist.
        this.bank_db = new DatabaseBuilder();
        this.bank_db.create_bank_database();
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
                        CreditCard card = createCreditCard();
                        loc = cardNums.indexOf(card.getCardNumber());
                    } while (loc > -1);
                    break;
                case 2: // Log into an account
                    Account credit_account = login();
                    if (credit_account.get_account_exists()) {
                        // Open the credit account navigation so the user can check the balance.

                        userSelection = accountNavigator(credit_account);
                    } else {
                        System.out.println("Wrong card number or PIN!");
                    }
                    break;
                case 3:
                    System.out.println("Holding...");
                default: // Exit the program
                    System.out.println("Bye!");
                    break;
            }
        } while (this.userSelection != 0);
        // Close Database connection
        this.bank_db.close_connection();
    }

    // Try to log the user into an account.
    public Account login() {
        // Prompt the user for their credit card login information
        System.out.println("Enter your card number:");
        long card_number = userInput.nextLong();
        System.out.println("Enter your PIN:");
        int pin = userInput.nextInt();

        // Determine if the credentials exist in the database table "card"
        return bank_db.get_account(card_number, pin);
    }

//    private int check_login_credentials(long card_number, int pin) {
//        int databaseIndex = cardNums.indexOf(card_number);
//        if (databaseIndex >= 0) {
//            // See if the pin is correct.
//            CreditCard userCard = cardOperator.get(databaseIndex);
//            if (userCard.checkPin(pin)) {
//                System.out.println("You have successfully logged in!");
//            } else {
//                System.out.println("Wrong card number or PIN!");
//            }
//        }
//        return databaseIndex;
//    }

    // Account Management Operator
    private int accountNavigator(Account credit_account) {
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
                accountNavigator(credit_account);
            }
            // Handle the user's selection
            switch (userSelect) {
                case 1: // Check Balance
                    System.out.println("Balance: " + credit_account.get_balance());
                    break;
                case 2: // Logout
                    System.out.println("You have successfully logged out!");
                    this.userSelection = 0;
                    break;
                default: // Exit program
                    System.out.println("Bye!");
                    break;
            }
        } while(userSelect != 0 && userSelect != 2);
        this.bank_db.close_connection();
        return 0;
    }

    // Create credit cards on demand
    private CreditCard createCreditCard() {
        CreditCard card = new CreditCard();

        // Add the credit card to the system.
        cardNums.add(card.getCardNumber());
        cardOperator.add(card);

        // Add the credit card to the database.
        this.bank_db.insert_new_card(card.getCardNumber(), card.getPin(), 0);
        // Print the card details for the user
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(card.getCardNumber());
        System.out.println("Your card PIN:");
        System.out.println(card.getPin());
        return new CreditCard();
    }
}
