package springbook.user.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
/*
    자바빈의 규약을 따르는 클래스에 생성자를 명시적으로 추가했을 때는
    파라미터가 없는 디폴트 생성자도 함꼐 정의해줘야 한다.
 */
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    private String name;
    private String password;

    private Level level;
    private Integer login;
    private Integer recommend;

    public void upgradeLevel() {
        Level nextLevel = this.level.nextLevel();
        if (nextLevel == null) {
            throw new IllegalStateException(this.level + "은 업그레이드가 불가능합니다");
        } else {
            this.level = nextLevel;
        }
    }
}
