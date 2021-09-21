package Simple_Banking_System;

import java.util.*;

class BankMenuController {
    private Scanner userInput;
    private int userSelection;
    private int table_id;
    ArrayList<CreditCard> cardOperator = new ArrayList<>();
    ArrayList<Long> cardNums = new ArrayList<Long>();
    DatabaseController bank_db;

    // Constructor
    BankMenuController() {
        this.userInput = new Scanner(System.in);
        this.userSelection = -1;

        // Create a new SQLite3 database if one does not already exist.
        this.bank_db = new DatabaseController();
        this.bank_db.create_bank_database();
    }

    // Method to process user's selection
    public void get_main_menu_selection() {
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
                get_main_menu_selection();
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

                    // Prompt the user for their credit card login information
                    System.out.println("Enter your card number:");
                    long card_number = userInput.nextLong();

                    // Determine if the credentials exist in the database table "card"
                    Account credit_account = bank_db.return_account_with_login(card_number, userInput);

                    // Open the credit account navigation so the user can check the balance.
                    if (credit_account.get_account_exists()) {
                        userSelection = navigate_in_account(credit_account);
                    }

                    break;
                case 3:
                    System.out.println("Holding...");
                default: // Exit the program
                    this.userSelection = 0;
                    System.out.println("Bye!");
                    break;
            }
        } while (this.userSelection != 0);

        // Close Database connection
        this.bank_db.close_connection();
    }

    // Account Management Operator once the user has logged in
    private int navigate_in_account(Account credit_account) {
        int account_operation = -1;
        do {
            // Print options
            System.out.println("1. Balance");
            System.out.println("2. Add income");
            System.out.println("3. Do transfer");
            System.out.println("4. Close account");
            System.out.println("5. Log out");
            System.out.println("0. Exit");

            // get user's selection
            try {
                account_operation = userInput.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Error. Please enter an integer selection 0-5");
                userInput.next(); // Clear input stream
                navigate_in_account(credit_account);
            }

            // Handle the user's selection
            switch (account_operation) {
                case 0: // Exit program
                    account_operation = 0;
                    this.userSelection = 0;
                    System.out.println("Bye!");
                    break;
                case 1: // Check Balance
                    account_operation = 1;

                    // Get the account balance from the DB
                    credit_account.set_card_balance(this.bank_db.get_account_balance(credit_account.get_card_number()));
                    System.out.println("Balance: " + credit_account.get_balance());
                    break;
                case 2: // Add income to the account
                    account_operation = 2;
                    System.out.println("Enter Income:");
                    int dollars = userInput.nextInt();
                    this.bank_db.update_balance(credit_account.get_card_number(), dollars);
                    System.out.println("Income was added!");
                    break;
                case 3: // Transfer balance
                    account_operation = 3;

                    // Get card number to transfer the funds TO
                    System.out.println("Transfer");
                    System.out.println("Enter card number:");
                    long recipient_card_number = userInput.nextLong();

                    // Check if the recipient account is the same as the sender's account
                    if (recipient_card_number == credit_account.get_card_number()) {
                        System.out.println("You can't transfer money to the same account!");
                        break;
                    }

                    // Pre-validate that the card number passes Luhn algorithm check-sum
                    boolean pass = this.bank_db.check_sum_luhn(recipient_card_number);
                    if (!pass) {
                        System.out.println("Probably you made a mistake in the card number.");
                        break;
                    }

                    // Check that the recipient account exists in DB
                    Account recipient_account = this.bank_db.return_existing_account(recipient_card_number);

                    if (recipient_account.get_account_exists()) {
                        // Get the amount to transfer
                        System.out.println("Enter how much money you want to transfer:");
                        int dollars_to_send = userInput.nextInt();

                        // Check if the sender has enough money in their account
                        int sender_balance = this.bank_db.get_account_balance(credit_account.get_card_number());
                        if (sender_balance < dollars_to_send) {
                            System.out.println("Not enough money!");
                        } else {
                            // Subtract the funds from the sender's account
                            this.bank_db.update_balance(credit_account.get_card_number(), -1 * dollars_to_send);

                            // Send Funds to the recipient account
                            this.bank_db.update_balance(recipient_card_number, dollars_to_send);

                            System.out.println("Success!");
                        }
                    }
                    break;
                case 4:
                    // Close account
                    account_operation = 0;
                    this.bank_db.delete_account(credit_account);
                    System.out.println("The account has been closed!");

                    // Once the user logs out, go back to main menu
                    get_main_menu_selection();
                    break;
                case 5:
                    // Logout
                    account_operation = 5;
                    this.userSelection = 0;
                    System.out.println("You have successfully logged out!");

                    // Once the user logs out, go back to main menu
                    get_main_menu_selection();
                    break;
                default:
                    account_operation = 0;
                    System.out.println("Unhandled selection: " + account_operation);
                    break;
            }
        } while(account_operation != 5 && account_operation != 0);
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
