package com.ledao.controller.admin;

import com.ledao.config.ConfigProperties;
import com.ledao.entity.*;
import com.ledao.service.BookService;
import com.ledao.service.BookTypeService;
import com.ledao.service.BorrowRecordService;
import com.ledao.service.UserService;
import com.ledao.util.DateUtil;
import com.ledao.util.StringUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
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
    private ConfigProperties configProperties;

    @Resource
    private BookService bookService;

    @Resource
    private BookTypeService bookTypeService;

    @Resource
    private BorrowRecordService borrowRecordService;

    @Resource
    private UserService userService;

    @Value("${bookImageFilePath}")
    private String bookImageFilePath;

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
    public Map<String, Object> save(Book book, @RequestParam("bookImage") MultipartFile file) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(16);
        //选择了图片
        if (!file.isEmpty()) {
            // 获取上传的文件名
            String fileName = file.getOriginalFilename();
            // 获取文件的后缀
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            //上传图片的新名称
            String newFileName = DateUtil.getCurrentDateStr2() + suffixName;
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(bookImageFilePath + newFileName));
            book.setImageName(newFileName);
            //找出同名的书
            List<Book> bookList = bookService.findListByName(book.getName());
            if (bookList.size() > 0) {
                Iterator<Book> bookIterator = bookList.iterator();
                //由于MySQL默认是大小写不敏感的("Java"和"java"是一样的),把不是真正同名的书籍移除
                while (bookIterator.hasNext()) {
                    Book book1 = bookIterator.next();
                    if (!book.getName().equals(book1.getName())) {
                        bookIterator.remove();
                    } else {//同名的书的图片也一样
                        book1.setImageName(newFileName);
                        bookService.update(book1);
                    }
                }
            }
        }
        //key值用于判断是否添加或修改成功
        int key;
        Book book1 = bookService.findByBookNumber(book.getBookNumber());
        //添加
        if (book.getId() == null) {
            if (book1 != null && book1.getBookNumber().equals(book.getBookNumber())) {
                resultMap.put("success", false);
                resultMap.put("errorInfo", "书号为" + book.getBookNumber() + "的图书已经存在,请重新输入!!");
                return resultMap;
            } else {
                List<Book> bookList = bookService.findListByName(book.getName());
                for (Book book2 : bookList) {
                    //同名的书籍已经存在就使用其引用的图片
                    if (book.getName().equals(book2.getName())) {
                        book.setImageName(book2.getImageName());
                        break;
                    }
                }
                key = bookService.add(book);
            }
        } else {//修改
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
        if (userService.findById(currentUser.getId()).getIsBorrow() == 2) {
            resultMap.put("success", false);
            resultMap.put("errorInfo", "借书失败，你的借书状态为不可借书，请联系管理员！！");
            return resultMap;
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("userId", currentUser.getId());
        map.put("state", 3);
        //获取逾期的借阅记录集合
        List<BorrowRecord> borrowRecordList = borrowRecordService.list(map);
        if (borrowRecordList.size() > 0) {
            resultMap.put("success", false);
            resultMap.put("errorInfo", "借书失败，你借阅的图书已逾期，请归还后再借阅图书！！");
            return resultMap;
        }
        map.put("state", 1);
        //获取正在借阅的借阅记录集合
        List<BorrowRecord> borrowRecordList2 = borrowRecordService.list(map);
        if (borrowRecordList2.size() >= configProperties.getMaxBorrowBookSize()) {
            resultMap.put("success", false);
            resultMap.put("errorInfo", "借书失败，你同时借阅的图书过多，每次最多同时借阅" + configProperties.getMaxBorrowBookSize() + "本图书！！");
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

    /**
     * 禁止图书被借出与否
     *
     * @param bookId
     * @param state
     * @return
     */
    @RequestMapping("/setBookCanBorrowOrNot")
    public Map<String, Object> setBookCanBorrowOrNot(Integer bookId, Integer state) {
        Map<String, Object> resultMap = new HashMap<>(16);
        Book book = bookService.findById(bookId);
        book.setState(state);
        int key = bookService.update(book);
        if (key > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }
}
