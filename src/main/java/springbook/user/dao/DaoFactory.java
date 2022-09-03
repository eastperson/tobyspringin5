package springbook.user.dao;

public class DaoFactory {
    public UserDao userDao() {
        ConnectionMaker connectionMaker = new DConnectionMaker();
        // 팩토리 메소드는 UserDao 타입의 오브젝트를 어떻게 만들고 준비시킬지 결정한다.
        UserDao userDao = new UserDao(connectionMaker);
        return userDao;
    }
}
