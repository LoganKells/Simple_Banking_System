/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package Simple_Banking_System;

public class App {
    public static void main(String[] args) {
        BankMenuController bank = new BankMenuController();
        bank.get_main_menu_selection();
        bank.bank_db.close_connection();
    }
}
