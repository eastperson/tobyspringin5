package springbook.learningtest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springbook.user.dao.DaoFactory;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { DaoFactory.class })
public class JUnitTest {

    // 테스트 컨텍스트가 매번 주입해주는 애플리케이션 컨텍스트가 항상 같은 오브젝트인지 테스트로 확인
    @Autowired ApplicationContext applicationContext;

    static Set<JUnitTest> testObjects = new HashSet<>();
    static ApplicationContext applicationContextObject = null;

    @Test
    public void test1() {
        assertThat(testObjects).doesNotContain(this);
        testObjects.add(this);
        assertThat(applicationContextObject == null || applicationContextObject == this.applicationContext).isTrue();
        applicationContextObject = this.applicationContext;
    }

    @Test
    public void test2() {
        assertThat(testObjects).doesNotContain(this);
        testObjects.add(this);
        assertThat(applicationContextObject == null || applicationContextObject == this.applicationContext).isTrue();
        applicationContextObject = this.applicationContext;
    }

    @Test
    public void test3() {
        assertThat(testObjects).doesNotContain(this);
        testObjects.add(this);
        assertThat(applicationContextObject == null || applicationContextObject == this.applicationContext).isTrue();
        applicationContextObject = this.applicationContext;
    }
}
