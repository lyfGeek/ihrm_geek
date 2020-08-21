package com.geek.shiro;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShiroSpringbootApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println(new Md5Hash("123456", "zhangsan", 3).toString());
    }

}
