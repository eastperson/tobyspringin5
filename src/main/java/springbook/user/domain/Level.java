package springbook.user.domain;

import lombok.Getter;

@Getter
public enum Level {

    // 세 개의 이늄 오브젝트 정의
    GOLD(3, null), SILVER(2, GOLD), BASIC(1, SILVER);

    private final int value;

    // 다음 단계의 레벨 정보를 스스로 갖고 있도록 Level 타입의 next 변수를 추가한다.
    private final Level next;

    // DB에 저장할 값을 넣어줄 생성자를 만들어둔다.
    // 값을 가져오는 메소드
    Level(int value, Level next) {
        this.value = value;
        this.next = next;
    }

    public Level nextLevel() {
        return this.next;
    }

    // 값으로부터 Level 타입 오브젝트를 가져오도록 만든 스태틱 메소드
    public static Level valueOf(int value) {
        switch (value) {
            case 1: return BASIC;
            case 2: return SILVER;
            case 3: return GOLD;
            default: throw new AssertionError("Unknown value: " + value);
        }
    }
}
