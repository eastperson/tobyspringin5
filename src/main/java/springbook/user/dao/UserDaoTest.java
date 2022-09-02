package springbook.user.dao;

public class UserDaoTest {
    public static void main(String[] args) {
        // UserDao 가 사용할 ConnectionMaker 구현 클래스를 결정하고 오브젝트를 만든다.
        ConnectionMaker connectionMaker = new DConnectionMaker();
        // UserDao 생성
        UserDao userDao = new UserDao(connectionMaker);
    }
}
