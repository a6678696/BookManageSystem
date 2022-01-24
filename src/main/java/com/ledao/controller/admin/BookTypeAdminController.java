package com.ledao.controller.admin;

import com.ledao.entity.BookType;
import com.ledao.entity.PageBean;
import com.ledao.service.BookTypeService;
import com.ledao.util.StringUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台图书类别Controller层
 *
 * @author LeDao
 * @company
 * @create 2022-01-21 23:57
 */
@RestController
@RequestMapping("/admin/bookType")
public class BookTypeAdminController {

    @Resource
    private BookTypeService bookTypeService;

    /**
     * 下拉框模糊查询
     *
     * @param q
     * @return
     */
    @RequestMapping("/comboList")
    public List<BookType> comboList(String q) {
        if (q == null) {
            q = "";
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("name", StringUtil.formatLike(q));
        return bookTypeService.list(map);
    }

    /**
     * 分页分条件查询图书类别
     *
     * @param bookType
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(BookType bookType, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        PageBean pageBean = new PageBean(page, rows);
        Map<String, Object> resultMap = new HashMap<>(16);
        Map<String, Object> map = new HashMap<>(16);
        map.put("name", StringUtil.formatLike(bookType.getName()));
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        List<BookType> bookTypeList = bookTypeService.list(map);
        Long total = bookTypeService.getCount(map);
        resultMap.put("rows", bookTypeList);
        resultMap.put("total", total);
        return resultMap;
    }

    /**
     * 添加或修改图书类别
     *
     * @param bookType
     * @return
     */
    @RequestMapping("/save")
    public Map<String, Object> save(BookType bookType) {
        Map<String, Object> resultMap = new HashMap<>(16);
        //key值用于判断是否添加或修改成功
        int key = 0;
        if (bookType.getId() == null) {
            BookType bookType1 = bookTypeService.findByName(bookType.getName());
            if (bookType1 != null && bookType1.getName().equals(bookType.getName())) {
                resultMap.put("success", false);
                resultMap.put("errorInfo", "名称为" + bookType.getName() + "的图书类别已经存在,请重新输入!!");
                return resultMap;
            } else {
                key = bookTypeService.add(bookType);
            }
        } else {
            BookType bookType1 = bookTypeService.findByName(bookType.getName());
            if (bookType1 != null && bookType1.getName().equals(bookType.getName()) && !bookType1.getId().equals(bookType.getId())) {
                resultMap.put("success", false);
                resultMap.put("errorInfo", "名称为" + bookType.getName() + "的图书类别已经存在,请重新输入!!");
                return resultMap;
            } else {
                key = bookTypeService.update(bookType);
            }
        }
        if (key > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }

    @RequestMapping("/delete")
    public Map<String, Object> delete(String ids) {
        Map<String, Object> resultMap = new HashMap<>(16);
        String[] idsStr = ids.split(",");
        //代表删除的个数,用于判断是否成功删除
        int deleteKey = 0;
        for (int i = 0; i < idsStr.length; i++) {
            int id = Integer.parseInt(idsStr[i]);
            bookTypeService.deleteById(id);
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
     * 检查图书类别id是否存在
     *
     * @param bookTypeId
     * @return
     */
    @RequestMapping("/checkBookTypeId")
    public Map<String, Object> checkBookTypeId(@RequestParam(value = "bookTypeId", required = false) Integer bookTypeId) {
        Map<String, Object> resultMap = new HashMap<>(16);
        BookType bookType = bookTypeService.findById(bookTypeId);
        if (bookType == null) {
            resultMap.put("success", false);
            resultMap.put("errorInfo", "你选择的图书类别不存在,请重新选择!!");
        } else {
            resultMap.put("success", true);
        }
        return resultMap;
    }
}
