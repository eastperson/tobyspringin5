package springbook;

import springbook.user.dao.DConnectionMaker;
import springbook.user.dao.DUserDao;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

import java.sql.SQLException;

public class SpringBookApplication {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new DUserDao();
        userDao.setConnectionMaker(new DConnectionMaker());

        User newUser = new User();
        newUser.setId("ep");
        newUser.setName("김동인");
        newUser.setPassword("kim");

        userDao.add(newUser);

        System.out.println(newUser.getId() + " 등록 성공");

        User ep = userDao.get(newUser.getId());
        System.out.println(ep.getName());
        System.out.println(ep.getPassword());
        System.out.println(ep.getId() + " 조회 성공");
    }
}
