package springbook.user.dao;

import lombok.Setter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingleConnectionDataSource implements DataSource{

    private String url;
    private String username;
    private String password;

    public SingleConnectionDataSource(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Connection getDataSource() throws SQLException {
        return DriverManager.getConnection(
                url, username, password);
    }
}
