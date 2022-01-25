package com.ledao.controller.admin;

import com.ledao.entity.*;
import com.ledao.service.BookService;
import com.ledao.service.BookTypeService;
import com.ledao.service.BorrowRecordService;
import com.ledao.util.StringUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台图书Controller层
 *
 * @author LeDao
 * @company
 * @create 2022-01-21 23:57
 */
@RestController
@RequestMapping("/admin/book")
public class BookAdminController {

    @Resource
    private BookService bookService;

    @Resource
    private BookTypeService bookTypeService;

    @Resource
    private BorrowRecordService borrowRecordService;

    /**
     * 分页分条件查询图书
     *
     * @param book
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(Book book, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        PageBean pageBean = new PageBean(page, rows);
        Map<String, Object> resultMap = new HashMap<>(16);
        Map<String, Object> map = new HashMap<>(16);
        map.put("name", StringUtil.formatLike(book.getName()));
        map.put("bookNumber", StringUtil.formatLike(book.getBookNumber()));
        map.put("bookTypeId", book.getBookTypeId());
        map.put("state", book.getState());
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        List<Book> bookList = bookService.list(map);
        for (Book book1 : bookList) {
            BookType bookType = bookTypeService.findById(book1.getBookTypeId());
            book1.setBookTypeName(bookType.getName());
        }
        Long total = bookService.getCount(map);
        resultMap.put("rows", bookList);
        resultMap.put("total", total);
        return resultMap;
    }

    /**
     * 添加或修改图书
     *
     * @param book
     * @return
     */
    @RequestMapping("/save")
    public Map<String, Object> save(Book book) {
        Map<String, Object> resultMap = new HashMap<>(16);
        //key值用于判断是否添加或修改成功
        int key;
        Book book1 = bookService.findByBookNumber(book.getBookNumber());
        if (book.getId() == null) {
            if (book1 != null && book1.getBookNumber().equals(book.getBookNumber())) {
                resultMap.put("success", false);
                resultMap.put("errorInfo", "书号为" + book.getBookNumber() + "的图书已经存在,请重新输入!!");
                return resultMap;
            } else {
                key = bookService.add(book);
            }
        } else {
            if (book1 != null && book1.getBookNumber().equals(book.getBookNumber()) && !book1.getId().equals(book.getId())) {
                resultMap.put("success", false);
                resultMap.put("errorInfo", "书号为" + book.getBookNumber() + "的图书已经存在,请重新输入!!");
                return resultMap;
            } else {
                key = bookService.update(book);
            }
        }
        if (key > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }

    /**
     * 删除图书,可批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Map<String, Object> delete(String ids) {
        Map<String, Object> resultMap = new HashMap<>(16);
        String[] idsStr = ids.split(",");
        //代表删除的个数,用于判断是否成功删除
        int deleteKey = 0;
        for (int i = 0; i < idsStr.length; i++) {
            int id = Integer.parseInt(idsStr[i]);
            bookService.deleteById(id);
            deleteKey++;
        }
        if (deleteKey > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }

    /**
     * 借书
     *
     * @param bookId
     * @param day
     * @return
     */
    @RequestMapping("/borrowBook")
    public Map<String, Object> borrowBook(HttpSession session, Integer bookId, Integer day) {
        User currentUser = (User) session.getAttribute("currentUser");
        Map<String, Object> resultMap = new HashMap<>(16);
        if (currentUser == null) {
            resultMap.put("success", false);
            resultMap.put("errorInfo", "借书失败，你的登录状态已过期，请刷新页面后重新登录再借书！！");
            return resultMap;
        }
        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setUserId(currentUser.getId());
        borrowRecord.setBookId(bookId);
        borrowRecord.setDay(day);
        int key = borrowRecordService.add(borrowRecord);
        if (key > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
            resultMap.put("errorInfo", "借书失败！！");
        }
        Book book = bookService.findById(bookId);
        book.setState(2);
        bookService.update(book);
        return resultMap;
    }

    /**
     * 续借,默认续借30天
     *
     * @param id
     * @return
     */
    @RequestMapping("/addBorrowRecordDay")
    public Map<String, Object> addBorrowRecordDay(Integer id) {
        Map<String, Object> resultMap = new HashMap<>(16);
        BorrowRecord borrowRecord = borrowRecordService.findById(id);
        borrowRecord.setDay(borrowRecord.getDay() + 30);
        int key = borrowRecordService.update(borrowRecord);
        if (key > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }

    /**
     * 归还
     *
     * @param id
     * @param bookId
     * @return
     */
    @RequestMapping("/returnBook")
    public Map<String, Object> returnBook(Integer id, Integer bookId) {
        Map<String, Object> resultMap = new HashMap<>(16);
        Book book = bookService.findById(bookId);
        book.setState(1);
        bookService.update(book);
        BorrowRecord borrowRecord = borrowRecordService.findById(id);
        borrowRecord.setState(2);
        int key = borrowRecordService.update(borrowRecord);
        if (key > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }
}
