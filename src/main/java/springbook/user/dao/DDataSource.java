package springbook.user.dao;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DDataSource implements DataSource {

    @Override
    public Connection getDataSource() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/springbook", "spring", "book");
    }
}