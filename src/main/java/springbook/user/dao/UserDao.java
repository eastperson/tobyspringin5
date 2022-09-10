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
    public void add(User user) throws ClassNotFoundException, SQLException {
        StatementStrategy addStatement = new AddStatement(user);
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
        // 선정한 전략 클래스의 오브젝트 생성
        StatementStrategy statementStrategy = new DeleteAllStatement();
        // 컨텍스트 호출. 전략 오브젝트 전달달
       jdbcContextWithStatementStrategy(statementStrategy);
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
