package com.ledao.service.impl;

import com.ledao.entity.BorrowRecord;
import com.ledao.mapper.BorrowRecordMapper;
import com.ledao.service.BorrowRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 借阅记录Service接口实现类
 *
 * @author LeDao
 * @company
 * @create 2022-01-24 10:26
 */
@Service("borrowRecordService")
public class BorrowRecordServiceImpl implements BorrowRecordService {

    @Resource
    private BorrowRecordMapper borrowRecordMapper;

    @Override
    public List<BorrowRecord> list(Map<String, Object> map) {
        return borrowRecordMapper.list(map);
    }

    @Override
    public Long getCount(Map<String, Object> map) {
        return borrowRecordMapper.getCount(map);
    }

    @Override
    public int add(BorrowRecord borrowRecord) {
        return borrowRecordMapper.add(borrowRecord);
    }

    @Override
    public int update(BorrowRecord borrowRecord) {
        return borrowRecordMapper.update(borrowRecord);
    }

    @Override
    public BorrowRecord findById(Integer id) {
        return borrowRecordMapper.findById(id);
    }

    @Override
    public int deleteById(Integer id) {
        return borrowRecordMapper.deleteById(id);
    }
}
