package springbook.user.dao;

import springbook.user.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class UserDao {

    private ConnectionMaker connectionMaker;

    public UserDao() {
        connectionMaker = new NConnectionMaker();
    }

    // User 등록
    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection connection = connectionMaker.makeNewConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into users(id, name, password) values (?,?,?)");
        preparedStatement.setString(1, user.getId());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setString(3, user.getPassword());

        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }

    // User 조회
    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection connection = connectionMaker.makeNewConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from users where id = ?");
        preparedStatement.setString(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        User user = new User();
        user.setId(resultSet.getString("id"));
        user.setId(resultSet.getString("id"));
        user.setName(resultSet.getString("name"));
        user.setPassword(resultSet.getString("password"));

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return user;
    }
}
