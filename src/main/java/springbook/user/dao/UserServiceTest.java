package springbook.user.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestDaoFactory.class, UserService.class })
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    List<User> userList;

    @Test
    public void bean() {
        assertThat(this.userService).isNotNull();
    }

    @BeforeEach
    public void setUp() {
        // 배열을 리스트로 만들어주는 편리한 메소드, 배열을 가변인자로 넣어주면 더욱 편리하다.
        userList = Arrays.asList(
                new User("ep", "김동인", "springno1", Level.BASIC, 1, 0),
                new User("nak", "김낙영", "springno2", Level.SILVER, 1, 0),
                new User("sik", "오윤식", "springno3", Level.GOLD, 1, 0)
        );
    }

    @Test
    public void upgradeLevels() {
        userDao.deleteAll();
        for (User user : userList) {
            userDao.add(user);
        }

        userService.upgradeLevels();

        checkLevel(userList.get(0), Level.BASIC);
        checkLevel(userList.get(1), Level.SILVER);
        checkLevel(userList.get(2), Level.GOLD);
    }

    private void checkLevel(User user, Level expectedLevel) {
        User userUpdate = userDao.get(user.getId());
        assertThat(userUpdate.getLevel()).isEqualTo(expectedLevel);
    }
}