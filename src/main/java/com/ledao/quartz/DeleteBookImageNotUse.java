package com.ledao.quartz;

import com.ledao.entity.Book;
import com.ledao.entity.BorrowRecord;
import com.ledao.service.BookService;
import com.ledao.service.impl.BookServiceImpl;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 每天23点自动删除无效的图片(图片名称不在t_book表中的)
 *
 * @author LeDao
 * @company
 * @create 2022-05-16 11:31
 */
@Configuration
@EnableScheduling
public class DeleteBookImageNotUse {

    @Resource
    private BookService bookService;

    @Value("${bookImageFilePath}")
    private String bookImageFilePath;

    @Scheduled(cron = "0 0 23 * * ?")
    public void work() {
        List<String> allUseImageList = bookService.findAllUseImage();
        File file = new File(bookImageFilePath);
        File[] files = file.listFiles();
        List<String> allImageList = new ArrayList<>();
        for (File file1 : files) {
            allImageList.add(file1.getName());
        }
        allImageList.removeAll(allUseImageList);
        int deleteNum = 0;
        for (String s : allImageList) {
            FileUtils.deleteQuietly(new File(bookImageFilePath + s));
            deleteNum++;
        }
        if (deleteNum > 0) {
            //获取当前时间
            LocalDateTime localDateTime = LocalDateTime.now();
            //格式化
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
            System.out.println(dateTimeFormatter.format(localDateTime) + ": 删除了" + deleteNum + "张图片");
        }
    }
}
