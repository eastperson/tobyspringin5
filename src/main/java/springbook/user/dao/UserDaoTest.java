package springbook.user.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.user.domain.User;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoTest {

    public UserDao userDao;

    @BeforeEach
    public void setUp() {
        // DaoFactory 를 설정정보로 사용하는 애플리케이션 컨텍스트 ApplicationContext 타입
        // @Configuration 이 붙은 자바 코드를 설정정보로 사용하기 위해서는 AnnotationConfigApplicationContext 사용
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);
        userDao = applicationContext.getBean("userDao", UserDao.class);
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
