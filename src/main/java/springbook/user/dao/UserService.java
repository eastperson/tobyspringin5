package springbook.user.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public void upgradeLevels() {
        List<User> users = userDao.getAll();
        for (User user : users) {
            // 레벨의 변화가 있는지 확인하는 플래그
            Boolean changed = null;

            if (user.getLevel() == Level.BASIC && user.getLogin() >= 50) {
                user.setLevel(Level.SILVER);
                changed = true;
            } else if (user.getLevel() == Level.SILVER && user.getRecommend() >= 30) {
                user.setLevel(Level.GOLD);
                changed = true;
            } else if (user.getLevel() == Level.GOLD) {
                changed = true;
            } else {
                changed = true;
            }

            if (changed) {
                userDao.update(user);
            }
        }
    }

    public void add(User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }
}
