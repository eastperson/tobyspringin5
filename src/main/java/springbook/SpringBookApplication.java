package springbook;

import springbook.user.dao.UserDao;
import springbook.user.domain.User;

import java.sql.SQLException;

public class SpringBookApplication {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();

        User user = new User();
        user.setId("ep");
        user.setName("김동인");
        user.setPassword("kim");

        userDao.add(user);
    }
}
