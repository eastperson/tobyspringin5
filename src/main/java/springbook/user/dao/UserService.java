package springbook.user.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECCOMENT_FOR_GOLD = 30;

    private final UserDao userDao;

    public void upgradeLevels() {
        List<User> users = userDao.getAll();
        for (User user : users) {
            if (canUpgradeLevel(user)) {
                upgradeLevel(user);
            }
        }
    }

    private void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
    }

    private boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        switch (currentLevel) {
            case BASIC: return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
            case SILVER: return (user.getRecommend() >= MIN_RECCOMENT_FOR_GOLD);
            case GOLD: return false;
            default: throw new IllegalArgumentException("Unknown Level: " + currentLevel);
        }
    }

    public void add(User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }
}
