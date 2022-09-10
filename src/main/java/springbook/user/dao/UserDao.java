package springbook.user.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springbook.user.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserDao {

    private ConnectionMaker connectionMaker;

    @Autowired
    public void setConnectionMaker(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
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
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionMaker.makeNewConnection();
            preparedStatement = connection.prepareStatement(
                    "select * from users where id = ?");
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
        }  catch (SQLException e) {
            throw  e;
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                    // preparedStatement.close()에서 SQLException이 발생할 수 있기 때문에 이를 잡아줘야 한다.
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {

                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {

                }
            }
        }

        User user = null;
        if (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getString("id"));
            user.setId(resultSet.getString("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        if (user == null) throw new RuntimeException();

        return user;
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // 예외가 발생할 가능성이 있는 코드를 모두 try 블록으로 묶어준다.
            connection = connectionMaker.makeNewConnection();
            preparedStatement = connection.prepareStatement("delete from users");
            preparedStatement.executeUpdate();
        // 예외가 발생했을 때 부가적인 작업을 해줄 수 있도록 catch 블록을 둔다. 아직은 예외를 다시 메소드 밖으로 던지는 것밖에 없다.
        } catch (Exception e) {
            throw e;
        // finally 이므로 try 블록에서 예외가 발생했을 때나 안 했을때나 모두 실행된다.
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                // preparedStatement.close()에서 SQLException이 발생할 수 있기 때문에 이를 잡아줘야 한다.
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {

                }
            }
        }
    }

    public int getCount() throws SQLException, ClassNotFoundException {
        Connection connection = connectionMaker.makeNewConnection();

        PreparedStatement ps = connection.prepareStatement("select count(*) from users");

        ResultSet resultSet = ps.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);

        resultSet.close();
        ps.close();
        connection.close();

        return count;
    }
}
