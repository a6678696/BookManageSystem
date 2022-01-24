package com.ledao;

import com.ledao.util.StringUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookManageSystemApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(StringUtil.isNumber("11所示11"));
    }

}
