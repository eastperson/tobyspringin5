package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NConnectionMaker implements ConnectionMaker {
    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        // N 사의 Connection 만들기
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/springbook", "spring", "book");
    }
}
