package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBTest {
    static final String URL = "jdbc:h2:./bankdb";
    static final String USER = "sa";
    static final String PASSWORD = "";

    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println(conn + "접속 성공!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
