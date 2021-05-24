package banking;

public class Main {
    public static void main(String[] args) {
        BankMenuController bank = new BankMenuController();
        bank.get_main_menu_selection();
        bank.bank_db.close_connection();
    }
}