package com.ledao.controller;

import com.ledao.entity.User;
import com.ledao.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 前台用户Controller层
 *
 * @author LeDao
 * @company
 * @create 2022-01-21 20:01
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户登录
     *
     * @param user
     * @param imageCode
     * @return
     */
    @ResponseBody
    @RequestMapping("/login")
    public Map<String, Object> login(User user, String imageCode, HttpSession session) {
        Map<String, Object> resultMap = new HashMap<>(16);
        String checkCode = (String) session.getAttribute("checkCode");
        //输入的验证码正确时
        if (checkCode.equals(imageCode)) {
            User currentUser = userService.findByUserName(user.getUserName());
            //数据库中存在该用户时
            if (currentUser != null) {
                //如果用户没有被封禁
                if (currentUser.getState() == 1) {
                    //密码正确时
                    if (user.getPassword().equals(currentUser.getPassword())) {
                        resultMap.put("currentUserType", currentUser.getType());
                        resultMap.put("success", true);
                        session.setAttribute("currentUser", currentUser);
                    } else {
                        resultMap.put("success", false);
                        resultMap.put("errorInfo", "用户名或密码错误!!");
                    }
                } else {
                    resultMap.put("success", false);
                    resultMap.put("errorInfo", "你的账号已被封禁!!");
                }
            } else {
                resultMap.put("success", false);
                resultMap.put("errorInfo", "用户名或密码错误!!");
            }
        } else {
            resultMap.put("success", false);
            resultMap.put("errorInfo", "你输入的验证码不正确,请重新输入!!");
        }
        return resultMap;
    }

    /**
     * 获取当前登录用户信息
     *
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/getUserInfo")
    public Map<String, Object> getUserInfo(HttpSession session) {
        Map<String, Object> resultMap = new HashMap<>(16);
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            resultMap.put("success", false);
        } else {
            resultMap.put("success", true);
            resultMap.put("currentUser", currentUser);
        }
        return resultMap;
    }

    /**
     * 注销登录
     *
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("currentUser");
        return "redirect:/login.html";
    }
}
