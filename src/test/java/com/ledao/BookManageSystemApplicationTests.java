package com.ledao;

import com.ledao.entity.User;
import com.ledao.service.BookService;
import com.ledao.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class BookManageSystemApplicationTests {

    @Resource
    private BookService bookService;

    @Value("${bookImageFilePath}")
    private String bookImageFilePath;

    @Test
    public void test() {
        List<String> allUseImageList = bookService.findAllUseImage();
        for (String s : allUseImageList) {
            System.out.println(s);
        }
    }

    @Test
    public void test2() {
        System.out.println("-----------------");
        List<String> allUseImageList = bookService.findAllUseImage();
        for (String s : allUseImageList) {
            System.out.println(s);
        }
        System.out.println(allUseImageList.size());
        System.out.println("-----------------");
        File file = new File(bookImageFilePath);
        File[] files = file.listFiles();
        List<String> allImageList = new ArrayList<>();
        for (File file1 : files) {
            allImageList.add(file1.getName());
        }
        for (String s : allImageList) {
            System.out.println(s);
        }
        System.out.println(allImageList.size());
        System.out.println("-----------------");
        allImageList.removeAll(allUseImageList);
        for (String s : allImageList) {
            System.out.println(s);
        }
        System.out.println(allImageList.size());
        System.out.println("-----------------");
    }
}