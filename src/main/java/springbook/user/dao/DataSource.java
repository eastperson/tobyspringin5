package springbook.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface DataSource {
    Connection getDataSource() throws SQLException;
}