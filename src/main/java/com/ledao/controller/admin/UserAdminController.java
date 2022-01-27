package com.ledao.controller.admin;

import com.ledao.entity.PageBean;
import com.ledao.entity.User;
import com.ledao.service.UserService;
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
 * 后台用户Controller层
 *
 * @author LeDao
 * @company
 * @create 2022-01-21 20:00
 */
@RestController
@RequestMapping("/admin/user")
public class UserAdminController {

    @Resource
    private UserService userService;

    /**
     * 分页条件查询用户
     *
     * @param user
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(User user, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        Map<String, Object> resultMap = new HashMap<>(16);
        Map<String, Object> map = new HashMap<>(16);
        PageBean pageBean = new PageBean(page, rows);
        map.put("userName", StringUtil.formatLike(user.getUserName()));
        map.put("state", user.getState());
        map.put("type", 2);
        map.put("isBorrow", user.getIsBorrow());
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        List<User> userList = userService.list(map);
        Long total = userService.getCount(map);
        resultMap.put("rows", userList);
        resultMap.put("total", total);
        return resultMap;
    }

    @RequestMapping("/save")
    public Map<String, Object> save(User user) {
        Map<String, Object> resultMap = new HashMap<>(16);
        int key = 0;
        User checkUserNameUser = userService.findByUserName(user.getUserName());
        //id为空时添加用户
        if (user.getId() == null) {
            //用户名不存在
            if (checkUserNameUser == null) {
                key = userService.add(user);
            } else {
                resultMap.put("success", false);
                resultMap.put("errorInfo", "用户名已经存在,请重新输入!!");
            }
        } else {
            key = userService.update(user);
        }
        if (key > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }

    /**
     * 封禁和解禁用户
     *
     * @param id
     * @param state
     * @return
     */
    @RequestMapping("/banUser")
    public Map<String, Object> banUser(Integer id, Integer state) {
        Map<String, Object> resultMap = new HashMap<>(16);
        User user = userService.findById(id);
        user.setState(state);
        userService.update(user);
        resultMap.put("success", true);
        return resultMap;
    }

    @RequestMapping("/delete")
    public Map<String, Object> delete(String ids) {
        Map<String, Object> resultMap = new HashMap<>(16);
        String[] idsStr = ids.split(",");
        int key = 0;
        for (int i = 0; i < idsStr.length; i++) {
            Integer id = Integer.valueOf(idsStr[i]);
            userService.deleteById(id);
            key++;
        }
        if (key > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }

    /**
     * 修改用户的借书状态
     *
     * @param id
     * @param isBorrow
     * @return
     */
    @RequestMapping("/modifyIsBorrow")
    public Map<String, Object> modifyIsBorrow(Integer id, Integer isBorrow) {
        Map<String, Object> resultMap = new HashMap<>(16);
        User user = userService.findById(id);
        user.setIsBorrow(isBorrow);
        userService.update(user);
        resultMap.put("success", true);
        return resultMap;
    }

    @RequestMapping("/modifyPassword")
    public Map<String, Object> modifyPassword(String newPassword, HttpSession session) {
        Map<String, Object> resultMap = new HashMap<>(16);
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            resultMap.put("success", false);
        } else {
            currentUser.setPassword(newPassword);
            userService.update(currentUser);
            resultMap.put("success", true);
        }
        return resultMap;
    }
}
