package com.ledao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author LeDao
 * @company
 * @create 2022-05-15 20:04
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String root(){
        return "redirect:/login.html";
    }
}
