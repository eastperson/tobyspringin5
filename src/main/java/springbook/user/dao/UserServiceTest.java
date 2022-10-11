package springbook.user.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestDaoFactory.class, UserService.class })
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    @Autowired
    DataSource dataSource;

    List<User> userList;

    @Test
    public void bean() {
        assertThat(this.userService).isNotNull();
    }

    @BeforeEach
    public void setUp() {
        // 배열을 리스트로 만들어주는 편리한 메소드, 배열을 가변인자로 넣어주면 더욱 편리하다.
        userList = Arrays.asList(
                new User("ep", "김동인", "springno1", Level.BASIC, 51, 0),
                new User("nak", "김낙영", "springno2", Level.SILVER, 1, 31),
                new User("sik", "오윤식", "springno3", Level.GOLD, 51, 0)
        );
    }

    @Test
    public void upgradeLevels() throws SQLException {
        userDao.deleteAll();
        for (User user : userList) {
            userDao.add(user);
        }

        userService.upgradeLevels();

        checkLevelUpgraded(userList.get(0), false);
        checkLevelUpgraded(userList.get(1), false);
        checkLevelUpgraded(userList.get(2), false);
    }

    private void checkLevelUpgraded(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if (upgraded) {
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel().nextLevel());
        } else {
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel());
        }
    }

    @Test
    public void add() {
        userDao.deleteAll();

        // GOLD 레벨이 이미 지정된 User 라면 레벨을 초기화하지 않아야 한다.
        User userWithLevel = userList.get(2);

        // 레벨이 비어있는 사용자. 로직에 따라 등록 중에 BASIC 레벨도 설정돼야 한다.
        User userWithoutLevel = userList.get(0);
        userWithoutLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertThat(userWithLevelRead.getLevel()).isEqualTo(userWithLevel.getLevel());
        assertThat(userWithoutLevelRead.getLevel()).isEqualTo(userWithoutLevel.getLevel());
    }

    @Test
    public void upgradeAllOrNothing() {
        UserService testUserService = new TestUserService(userDao, userList.get(1).getId());
        testUserService.setDataSource(dataSource);
        userDao.deleteAll();
        for (User user: userList) {
            userDao.add(user);
        }

        try {
            // TestUserService 는 업그레이드 작업 중에 예외가 발생해야 한다. 정상 종료라면 문제가 있으니 실패
            // TestUSerService 가 던져주는 예외를 잡아서 계속 진행되도록 한다. 그 외의 예외라면 테스트 실패
            assertThatThrownBy(testUserService::upgradeLevels).isInstanceOf(TestUserServiceException.class);
        } catch (TestUserServiceException e) {
        }
        // 예외가 발생하기 전에 레벨 변경이 있었던 사용자의 레벨이 처음 상태로 바뀌었나 확인인
        checkLevelUpgraded(userList.get(1), false);
    }

    static class TestUserService extends UserService {
        private String id;

        public TestUserService(UserDao userDao, String id) {
            super(userDao);
            this.id = id;
        }

        @Override
        protected void upgradeLevel(User user) {
            if (user.getId().equals(this.id)) {
                throw new TestUserServiceException();
            }
            super.upgradeLevel(user);
        }
    }

    static class TestUserServiceException extends RuntimeException {

    }
}
