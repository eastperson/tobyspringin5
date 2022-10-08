package springbook.user.domain;

import lombok.Getter;

@Getter
public enum Level {

    // 세 개의 이늄 오브젝트 정의
    BASIC(1), SILVER(2), GOLD(3);

    private final int value;

    // DB에 저장할 값을 넣어줄 생성자를 만들어둔다.
    // 값을 가져오는 메소드
    Level(int value) {
        this.value = value;
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
