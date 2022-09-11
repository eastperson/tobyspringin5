package springbook.learningtest.template;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class CalcSumTest {

    // 픽스쳐 생성
    Calculator calculator;
    String numFilepath;

    @BeforeEach
    void setUp() {
        this.calculator = new Calculator();
        this.numFilepath = getClass().getResource("/numbers.txt").getPath();
    }

    @Test
    void sumOfNumbers() throws IOException {
        int sum = calculator.calcSum(numFilepath);
        assertThat(sum).isEqualTo(10);
    }

    @Test
    void multiplyOfNumbers() throws IOException {
        int result = calculator.calcMultiply(numFilepath);
        assertThat(result).isEqualTo(24);
    }

    @Test
    void concatenate() throws IOException {
        String result = calculator.concatenate(numFilepath);
        assertThat(result).isEqualTo("1234");
    }
}
