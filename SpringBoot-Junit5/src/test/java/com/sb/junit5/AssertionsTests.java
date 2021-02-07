package com.sb.junit5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @title: AssertionsTests
 * @Author Wen
 * @Date: 2021/2/5 17:46
 * @Version 1.0
 */
@SpringBootTest
public class AssertionsTests {

    /**
     * 断言：前面断言失败，后面的代码都不会执行
     */
    @DisplayName("简单断言")
    @Test
    void testSimpleAssertions() {
        int cal = cal(2, 3);
        //assertEquals()是静态方法,可以直接使用
        assertEquals(5, cal, "逻辑计算失败");

        Object obj1 = new Object();
        Object obj2 = new Object();


        assertSame(obj1, obj2, "objs is not same");

    }

    int cal(int i, int j) {
        return i + j;
    }

    @DisplayName("数组断言")
    @Test
    void testArrayAssert() {
        assertArrayEquals(new int[]{2, 1}, new int[]{1, 2}, "数组不相等");
    }


    @DisplayName("组合断言")
    @Test
    void testAllAssert() {
        assertAll("test",
                () -> assertTrue(true && true, "结果为ture"),
                () -> assertEquals(1, 2, "结果不相等")
        );
    }

    @DisplayName("异常断言")
    @Test
    void testExceptionAssert() {
        assertThrows(ArithmeticException.class,
                () -> {
                    int i = 10 / 2;
                },
                "业务计算正确"
        );
    }

    @Test
    @DisplayName("fail")
    public void shouldFail() {
       if ( 1 == 2){
           fail("this fail");
       }
    }


    @Test
    @DisplayName("超时测试")
    public void timeoutTest() {
        //如果测试方法时间超过1s将会异常
        assertTimeout(Duration.ofMillis(500),()->Thread.sleep(1000),"超时");
    }
}