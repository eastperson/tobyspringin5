package springbook.user.dao;

import org.springframework.beans.factory.annotation.Autowired;
import springbook.user.dao.strategy.StatementStrategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {

    private ConnectionMaker connectionMaker;

    public void setConnectionMaker(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void workWithStatementStrategy(StatementStrategy statementStrategy) throws SQLException, ClassNotFoundException {
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

    public void executeSql(final String query) throws SQLException, ClassNotFoundException {
        workWithStatementStrategy(
                connection -> connection.prepareStatement(query)
        );
    }
}
