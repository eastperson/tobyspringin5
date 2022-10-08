package springbook.user.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Component;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import java.sql.SQLException;
import java.util.List;

@Component
public class UserDaoJdbc implements UserDao {

    private JdbcTemplate jdbcTemplate;
    private javax.sql.DataSource dataSource;
    private ConnectionMaker connectionMaker;

    public void setConnectionMaker(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public UserDaoJdbc() {
        this.setDataSource();
    }

    // 수정자 메소드이면서 jdbcContext에 대한 생성, DI 작업을 수행한다.
    public void setDataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost:3306/springbook");
        dataSource.setUsername("spring");
        dataSource.setPassword("book");
        // JdbcContext 생성
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }

    private RowMapper<User> userMapper = (resultSet, rowNum) -> {
        User user = new User();
        user.setId(resultSet.getString("id"));
        user.setName(resultSet.getString("name"));
        user.setPassword(resultSet.getString("password"));
        user.setLevel(Level.valueOf(resultSet.getInt("level")));
        user.setLogin(resultSet.getInt("login"));
        user.setRecommend(resultSet.getInt("recommend"));
        return user;
    };

    // User 등록
    public void add(final User user) {
        this.jdbcTemplate.update(
                "insert into users(id, name, password, level, login, recommend) values (?,?,?,?,?,?)",
                user.getId(),
                user.getName(),
                user.getPassword(),
                user.getLevel().getValue(),
                user.getLogin(),
                user.getRecommend()
        );
    }

    // User 조회
    public User get(String id) {
        return this.jdbcTemplate.queryForObject("select * from users where id = ?",
                new Object[]{id},
                // ResultSet 한 로우의 결과를 오브젝트에 매핑해주는 RowMapper 콜백
                this.userMapper
        );
    }

    public void deleteAll() {
        this.jdbcTemplate.update("delete from users");
    }



    public int getCount() {
        return this.jdbcTemplate.query(con -> con.prepareStatement("select count(*) from users"),
            resultSet -> {
            resultSet.next();
            return resultSet.getInt(1);
        });
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query("select * from users order by id",
            this.userMapper
        );
    }
}
