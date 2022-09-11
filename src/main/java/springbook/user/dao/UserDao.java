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

    private JdbcContext jdbcContext;
    private DataSource dataSource;
    private ConnectionMaker connectionMaker;

    public void setConnectionMaker(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    // 수정자 메소드이면서 jdbcContext에 대한 생성, DI 작업을 수행한다.
    public void setDataSource(DataSource dataSource) {
        // JdbcContext 생성
        this.jdbcContext = new JdbcContext();
        // 의존오브젝트 주입(DI)
        this.jdbcContext.setConnectionMaker(new DConnectionMaker(dataSource));
        // 아직 JdbcContext를 적용하지 않은 메소드를 위해 저장해둔다.
        this.dataSource = dataSource;
    }

    // User 등록
    public void add(final User user) throws ClassNotFoundException, SQLException {
        this.jdbcContext.workWithStatementStrategy(
                connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            "insert into users(id, name, password) values (?,?,?)");
                    preparedStatement.setString(1, user.getId());
                    preparedStatement.setString(2, user.getName());
                    preparedStatement.setString(3, user.getPassword());
                    return preparedStatement;
                }
        );
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
        this.jdbcContext.executeSql("delete from users");
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
