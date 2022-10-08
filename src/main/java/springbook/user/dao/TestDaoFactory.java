package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 스프링이 빈 팩토리를 위한 오브젝트 설정을 담당하는 클래스라고 인식할 수 있도록 @Configuration 추가
@Configuration
public class TestDaoFactory {

    @Bean
    public UserDaoJdbc userDao() {
        UserDaoJdbc userDaoJdbc = new UserDaoJdbc();
        DataSource dataSource = new SingleConnectionDataSource("jdbc:mysql://localhost:3306/springbook", "spring", "book");
        ConnectionMaker connectionMaker = new DConnectionMaker(dataSource);
        userDaoJdbc.setConnectionMaker(connectionMaker);
        return userDaoJdbc;
    }

    public AccountDao accountDao() {
        return new AccountDao(connectionMaker());
    }

    public MessageDao messageDao() {
        return new MessageDao(connectionMaker());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new DConnectionMaker(new DDataSource());
    }
}
