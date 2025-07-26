package com.example.monoproj.simple;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    @Test
    void testAdd() {
        Calculator calculator = new Calculator();
        int result = calculator.add(1, 2);
        assertEquals(3, result);
    }
}

// 왜 테스트 코드를 작성하는지?
// 오류에 대한건 실행을 하면 바로 나오는 부분이라 검증은 아님.
// 탑다운식으로 테스트 코드를 작성하여 뭘 구현하고 싶은지에 대해 정의?
