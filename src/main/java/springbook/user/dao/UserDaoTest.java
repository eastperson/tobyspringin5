package springbook.user.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springbook.user.domain.User;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

/*
    테스트 메소드에서 애플리케이션 컨텍스트의 구성이나 상태를 변경한다는
    것을 테스트 컨텍스트 프레임워크에 알려준다.
 */
// 스프링 테스트 컨텍스트 프레임워크의 JUnit 확장 기능 지정
@ExtendWith(SpringExtension.class)
// 테스트 컨텍스트가 자동으로 만들어줄 애플리케이션 컨텍스트 지정
@ContextConfiguration(classes = { TestDaoFactory.class })
@DirtiesContext
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @BeforeEach
    public void setUp() {
        DataSource dataSource = new SingleConnectionDataSource("jdbc:mysql://localhost:3306/springbook", "spring", "book");
        ConnectionMaker connectionMaker = new DConnectionMaker(dataSource);
        userDao.setConnectionMaker(connectionMaker);
    }

    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        User user1 = new User("ep", "김동인", "springno1");
        User user2 = new User("nak", "김낙영", "springno2");
        userDao.add(user1);
        userDao.add(user2);

        User userget1 = userDao.get(user1.getId());
        assertThat(user1.getName()).isEqualTo(userget1.getName());
        assertThat(user1.getPassword()).isEqualTo(userget1.getPassword());

        User userget2 = userDao.get(user2.getId());
        assertThat(user2.getName()).isEqualTo(userget2.getName());
        assertThat(user2.getPassword()).isEqualTo(userget2.getPassword());
        /*
        springbook.user.dao.UserDao@74e52303
        springbook.user.dao.UserDao@74e52303
        true
         */
    }

    @Test
    public void count() throws SQLException, ClassNotFoundException {
        User user1 = new User("ep", "김동인", "springno1");
        User user2 = new User("nak", "김낙영", "springno2");
        User user3 = new User("sik", "오윤식", "springno3");

        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        userDao.add(user1);
        assertThat(userDao.getCount()).isEqualTo(1);

        userDao.add(user2);
        assertThat(userDao.getCount()).isEqualTo(2);

        userDao.add(user3);
        assertThat(userDao.getCount()).isEqualTo(3);
    }
}
