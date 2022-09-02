package springbook.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class DConnectionMaker implements ConnectionMaker {
    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        // D 사의 Connection 만들기
        return null;
    }
}
