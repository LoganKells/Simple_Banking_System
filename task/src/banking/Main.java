package banking;

public class Main {
    public static void main(String[] args) {
        BankHandler bank = new BankHandler();
        DatabaseBuilder dbBuilder = new DatabaseBuilder();
        dbBuilder.createDatabase();
        dbBuilder.execute_sql_update("INSERT INTO REGISTRATION " +
                "(id, first, last, age) " +
                "VALUES (1, 'Logan', 'Kells', 29)");
        //dbBuilder.deleteTable("REGISTRATION");
        //bank.getUserSelection();
    }
}