package springbook.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class NUserDao extends UserDao {
    @Override
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        return null;
    }
}
