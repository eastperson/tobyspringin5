package springbook.user.dao;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class DConnectionMaker implements ConnectionMaker {

    private final DataSource dataSource;

    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        // D 사의 Connection 만들기
        Class.forName("com.mysql.cj.jdbc.Driver");
        return dataSource.getDataSource();
    }
}
