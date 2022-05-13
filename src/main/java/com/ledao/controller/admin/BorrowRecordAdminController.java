package com.ledao.controller.admin;

import com.ledao.entity.Book;
import com.ledao.entity.BorrowRecord;
import com.ledao.entity.PageBean;
import com.ledao.entity.User;
import com.ledao.service.BookService;
import com.ledao.service.BorrowRecordService;
import com.ledao.service.UserService;
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

    @Resource
    private UserService userService;

    /**
     * 分页分条件查询借阅记录,普通用户
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
        //用户的登录状态过期
        if (currentUser == null) {
            map.put("userId", -1);
        } else {
            //是普通用户
            if (currentUser.getType() == userType) {
                map.put("userId", currentUser.getId());
            }
        }
        map.put("state", borrowRecord.getState());
        List<Integer> idListBook;
        if (StringUtil.isNotEmpty(borrowRecord.getBookName())) {
            idListBook = bookService.selectIdByNameLike(StringUtil.formatLike(borrowRecord.getBookName()));
            if (idListBook.size() == 0) {
                idListBook.add(-1);
            }
            map.put("idListBook", idListBook);
        }
        List<BorrowRecord> borrowRecordList = borrowRecordService.list(map);
        for (BorrowRecord record : borrowRecordList) {
            Book book = bookService.findById(record.getBookId());
            record.setBookName(book.getName());
            record.setReturnTime(DateUtil.dateAddDays(record.getTime(), record.getDay()));
            record.setImageName(book.getImageName());
        }
        Long total = borrowRecordService.getCount(map);
        resultMap.put("rows", borrowRecordList);
        resultMap.put("total", total);
        return resultMap;
    }

    /**
     * 分页分条件查询借阅记录,管理员
     *
     * @param borrowRecord
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list2")
    public Map<String, Object> list2(BorrowRecord borrowRecord, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        PageBean pageBean = new PageBean(page, rows);
        Map<String, Object> resultMap = new HashMap<>(16);
        Map<String, Object> map = new HashMap<>(16);
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        map.put("state", borrowRecord.getState());
        List<Integer> idListBook;
        if (StringUtil.isNotEmpty(borrowRecord.getBookName())) {
            idListBook = bookService.selectIdByNameLike(StringUtil.formatLike(borrowRecord.getBookName()));
            if (idListBook.size() == 0) {
                idListBook.add(-1);
            }
            map.put("idListBook", idListBook);
        }
        List<Integer> idListUser;
        if (StringUtil.isNotEmpty(borrowRecord.getUserName())) {
            idListUser = userService.selectIdByUserNameLike(StringUtil.formatLike(borrowRecord.getUserName()));
            if (idListUser.size() == 0) {
                idListUser.add(-1);
            }
            map.put("idListUser", idListUser);
        }
        List<BorrowRecord> borrowRecordList = borrowRecordService.list(map);
        for (BorrowRecord record : borrowRecordList) {
            Book book = bookService.findById(record.getBookId());
            record.setBookName(book.getName());
            record.setImageName(book.getImageName());
            record.setReturnTime(DateUtil.dateAddDays(record.getTime(), record.getDay()));
            record.setUserName(userService.findById(record.getUserId()).getUserName());
        }
        Long total = borrowRecordService.getCount(map);
        resultMap.put("rows", borrowRecordList);
        resultMap.put("total", total);
        return resultMap;
    }
}
