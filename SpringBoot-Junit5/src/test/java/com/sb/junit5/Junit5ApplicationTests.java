package com.sb.junit5;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@DisplayName("junit5功能测试类")
/**
 * @SpringBootTest注解使用了spring的驱动进行测试,因此可以使用@Autowired进行注入
 * @BootstrapWith(SpringBootTestContextBootstrapper.class)
 * @ExtendWith({SpringExtension.class})
 */
@SpringBootTest
class Junit5ApplicationTests {


    @DisplayName("测试testDisplayName()") //标注测试名称
    @Test
    void testDisplayName()
    {
        System.out.println("this is display name");
    }

    /**
     * 所有测试方法前都执行一次
     */
    @BeforeEach
    void testBeforeEach()
    {
        System.out.println("test is starting");
    }

    /**
     * 所有测试方法后都执行一次
     */
    @AfterEach
    void testAfterEach()
    {
        System.out.println("test is ending");
    }


    @Disabled   //该测试方法不行执
    @DisplayName("test01")
    @Test
    void test01()
    {
        System.out.println("this is test01");
    }

    /**
     * 在该测试类最后执行一次
     */
    @AfterAll
    static void testAfterAll()
    {
        System.out.println("test after all is ending");
    }

    /**
     * 在该测试类前执行一次
     */
    @BeforeAll
    static void testBeforeAll()
    {
        System.out.println("test before all is starting");
    }

    /**
     * 规定方法超时时间。超出时间测试出异常
     * @throws InterruptedException
     */
    @Test
    @Timeout(value = 500,unit = TimeUnit.MILLISECONDS)
    void testTimeOut() throws InterruptedException
    {
        Thread.sleep(600);
    }

    /**
     * 重复测试该方法3次
     */
    @RepeatedTest(3)
    void testRepeatedTest()
    {
        System.out.println(1);
    }
}
