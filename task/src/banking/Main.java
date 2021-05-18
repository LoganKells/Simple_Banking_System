package banking;

public class Main {
    public static void main(String[] args) {
        BankController bank = new BankController();
        bank.getUserSelection();
        bank.bank_db.close_connection();
    }
}