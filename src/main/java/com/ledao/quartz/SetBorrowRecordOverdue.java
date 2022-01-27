package com.ledao.quartz;

import com.ledao.entity.BorrowRecord;
import com.ledao.service.BorrowRecordService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;

/**
 * 每分钟自动检测用户正在借阅的书是否逾期,如果逾期就修改借阅记录的状态为逾期
 *
 * @author LeDao
 * @company
 * @create 2022-01-27 20:57
 */
@Configuration
@EnableScheduling
public class SetBorrowRecordOverdue {

    @Resource
    private BorrowRecordService borrowRecordService;

    @Scheduled(cron = "0 */1 * * * ?")
    public void work() {
        List<BorrowRecord> borrowRecordList= borrowRecordService.getBorrowRecordListOverdue();
        for (BorrowRecord borrowRecord : borrowRecordList) {
            borrowRecord.setState(3);
            borrowRecordService.update(borrowRecord);
        }
    }
}
