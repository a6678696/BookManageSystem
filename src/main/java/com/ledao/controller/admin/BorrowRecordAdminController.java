package com.ledao.controller.admin;

import com.ledao.entity.BorrowRecord;
import com.ledao.entity.PageBean;
import com.ledao.entity.User;
import com.ledao.service.BookService;
import com.ledao.service.BorrowRecordService;
import com.ledao.util.DateUtil;
import com.ledao.util.StringUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台借阅记录Controller层
 *
 * @author LeDao
 * @company
 * @create 2022-01-24 18:41
 */
@RestController
@RequestMapping("/admin/borrowRecord")
public class BorrowRecordAdminController {

    @Resource
    private BorrowRecordService borrowRecordService;

    @Resource
    private BookService bookService;

    /**
     * 分页分条件查询借阅记录
     *
     * @param borrowRecord
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(BorrowRecord borrowRecord, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        PageBean pageBean = new PageBean(page, rows);
        Map<String, Object> resultMap = new HashMap<>(16);
        Map<String, Object> map = new HashMap<>(16);
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        //2为普通用户身份
        int userType = 2;
        //用户的登录状态没有过期并且是普通用户
        if (currentUser != null && currentUser.getType() == userType) {
            map.put("userId", currentUser.getId());
        } else {
            map.put("userId", -1);
        }
        map.put("state", borrowRecord.getState());
        List<Integer> idList;
        if (StringUtil.isNotEmpty(borrowRecord.getBookName())) {
            idList = bookService.selectIdByNameLike(StringUtil.formatLike(borrowRecord.getBookName()));
            if (idList.size() == 0) {
                idList.add(-1);
            }
            map.put("idList", idList);
        }
        List<BorrowRecord> borrowRecordList = borrowRecordService.list(map);
        for (BorrowRecord record : borrowRecordList) {
            record.setBookName(bookService.findById(record.getBookId()).getName());
            record.setReturnTime(DateUtil.dateAddDays(record.getTime(), record.getDay()));
        }
        Long total = borrowRecordService.getCount(map);
        resultMap.put("rows", borrowRecordList);
        resultMap.put("total", total);
        return resultMap;
    }
}
