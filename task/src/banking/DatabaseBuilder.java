package banking;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseBuilder {
    String myURL = null;

    DatabaseBuilder() {
        this.myURL = "jdbc:sqlite:Simple Banking System/task/resources/bank.db";
    }

    public void createDatabase() {
        // Instantiate a connection to the database
        Connection conn = open_connection(this.myURL);

        // Create a table
        try {
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS REGISTRATION" +
                    "(id INTEGER not NULL, " +
                    " first VARCHAR(255), " +
                    " last VARCHAR(255), " +
                    " age INTEGER, " +
                    " PRIMARY KEY ( id ))";
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
