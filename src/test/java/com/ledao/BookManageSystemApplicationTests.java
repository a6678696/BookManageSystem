package com.ledao;

import com.ledao.entity.Book;
import com.ledao.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
class BookManageSystemApplicationTests {
    @Resource
    private BookService bookService;

    @Test
    public void test() {
        Book book = bookService.findById(1000);
        if (book == null) {
            log.info("图书不存在");
            System.out.println("图书不存在");
        }
        System.out.println(book);
    }
}