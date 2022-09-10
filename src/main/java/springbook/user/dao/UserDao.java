package springbook.user.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springbook.user.dao.strategy.AddStatement;
import springbook.user.dao.strategy.DeleteAllStatement;
import springbook.user.dao.strategy.StatementStrategy;
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

    public void jdbcContextWithStatementStrategy(StatementStrategy statementStrategy) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionMaker.makeNewConnection();
            preparedStatement = statementStrategy.makePreparedStatement(connection);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (preparedStatement != null) { try { preparedStatement.close(); } catch (SQLException e) {} }
            if (connection != null) { try { connection.close(); } catch (SQLException e) {} }
        }
    }

    // User 등록
    public void add(final User user) throws ClassNotFoundException, SQLException {
        // add() 메소드 내부에 선언된 로컬 클래스다.
        class AddStatement implements StatementStrategy {

            @Override
            public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "insert into users(id, name, password) values (?,?,?)");
                preparedStatement.setString(1, user.getId());
                preparedStatement.setString(2, user.getName());
                preparedStatement.setString(3, user.getPassword());
                return preparedStatement;
            }
        }

        StatementStrategy addStatement = new AddStatement();
        jdbcContextWithStatementStrategy(addStatement);
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
        jdbcContextWithStatementStrategy(
                connection -> connection.prepareStatement("delete from users")
        );
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
