package Simple_Banking_System;

public class Account {
    boolean account_exists;
    long card_number;
    int card_pin;
    int card_balance;

    Account() {
        this.account_exists = false;
        this.card_number = 0;
        this.card_pin = 0;
        this.card_balance = 0;
    }
    Account(boolean account_exists, long card_number, int card_pin, int card_balance) {
        this.account_exists = account_exists;
        this.card_number = card_number;
        this.card_pin = card_pin;
        this.card_balance = card_balance;
    }

    public void set_account_exists(boolean value) {
        this.account_exists = value;
    }

    public void set_card_number(long number) {
        this.card_number = number;
    }

    public void set_card_pin(int pin) {
        this.card_pin = pin;
    }

    public void set_card_balance(int balance) {
        this.card_balance = balance;
    }

    public boolean get_account_exists() {
        return this.account_exists;
    }

    public int get_balance() {
        return this.card_balance;
    }

    public Long get_card_number() {
        return this.card_number;
    }
}
