package springbook.user.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.user.domain.User;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // DaoFactory 를 설정정보로 사용하는 애플리케이션 컨텍스트 ApplicationContext 타입
        // @Configuration 이 붙은 자바 코드를 설정정보로 사용하기 위해서는 AnnotationConfigApplicationContext 사용
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao userDao = applicationContext.getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("user");
        user.setName("김동인");
        user.setPassword("single");

        userDao.add(user);

        User user2 = userDao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());
        System.out.println(user2.getId() + " 조회 성공");
        /*
        springbook.user.dao.UserDao@74e52303
        springbook.user.dao.UserDao@74e52303
        true
         */
    }
}
