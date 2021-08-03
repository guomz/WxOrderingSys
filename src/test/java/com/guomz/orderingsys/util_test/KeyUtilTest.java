package com.guomz.orderingsys.util_test;

import com.guomz.orderingsys.util.KeyUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class KeyUtilTest {

    @Test
    public void testGenerateKey(){
        System.out.println(KeyUtil.generateKey());
    }
}
