package banking;
import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class DatabaseBuilder {
    String myURL = null;
    Connection conn;

    DatabaseBuilder() {
        // this.myURL = "jdbc:sqlite:Simple Banking System/task/resources/bank.db";
        this.myURL = "jdbc:sqlite:card.s3db";
        this.conn = open_connection(this.myURL); // Instantiate a connection to the database
    }

    public void create_bank_database() {

        // Create a table
        try {
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS card" +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " number TEXT, " +
                    " pin TEXT, " +
                    " balance INTEGER DEFAULT 0)";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void close_connection() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert_new_card(Long card_number, Integer card_pin, Integer balance) {
        String sql = "INSERT INTO card(number, pin, balance) VALUES(?,?,?)";

        try (Connection conn = this.open_connection(this.myURL);
             PreparedStatement prepared_stmt = conn.prepareStatement(sql)) {
                prepared_stmt.setString(1, card_number.toString());
                prepared_stmt.setString(2, card_pin.toString());
                prepared_stmt.setInt(3, balance);
                prepared_stmt.executeUpdate();
        } catch (SQLException e) {
                System.out.println(e.getMessage());
        }
    }

    public Account get_account(Long card_number, Integer card_pin) {
        Account credit_account_return = new Account();

        String sql = "SELECT id, number, pin, balance FROM card where number = ? AND pin = ?";

        try (Connection conn = this.open_connection(this.myURL);
             PreparedStatement prepared_stmt = conn.prepareStatement(sql)) {
            prepared_stmt.setString(1, card_number.toString());
            prepared_stmt.setString(2, card_pin.toString());
            ResultSet result = prepared_stmt.executeQuery();

            // Read the results
            result.next();

            // Print the results to console
//            System.out.println(result.getInt("id") + "\t" +
//                    result.getString("number") + "\t" +
//                    result.getString("pin") + "\t" +
//                    result.getDouble("balance"));

            // If the account was found then package the results in a class for returning.
            long card_number_result = Long.parseLong(result.getString("number"));
            int card_pin_result = Integer.parseInt(result.getString("pin"));
            int card_balance_result = result.getInt("balance");
            if (card_number_result == card_number) {
                System.out.println("You have successfully logged in!");
                credit_account_return.set_account_exists(true);
                credit_account_return.set_card_number(card_number_result);
                credit_account_return.set_card_pin(card_pin_result);
                credit_account_return.set_card_balance(card_balance_result);
            }
        } catch (SQLException e) {
            // e.printStackTrace();
        }
        // Create and return the credit account indicating if the account exists.
        return credit_account_return;
    }

    public void execute_sql_update(String sql) {
        // Instantiate a connection to the database
        Connection conn = open_connection(this.myURL);

        // Update values
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteTable(String tableName) {
        Connection myConnect = null;
        try {
            // Instantiate a connection to the database
            myConnect = open_connection(this.myURL);

            // Drop the table. WARNING this deletes all the data.
            Statement stmt = myConnect.createStatement();
            String sql = "DROP TABLE " + tableName;
            stmt.executeUpdate(sql);
            System.out.println("Deleted table: " + tableName);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (myConnect != null) {
                    myConnect.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Connection open_connection(String databaseURL) {
        Connection connection = null;
        try {
            SQLiteDataSource source = new SQLiteDataSource();
            source.setUrl(databaseURL);
            connection = source.getConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }
}
