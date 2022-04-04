package com.wgx.dormitorymanager2.controller;

import com.wgx.dormitorymanager2.bean.Administrator;
import com.wgx.dormitorymanager2.bean.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * author:wgx
 * version:1.0
 */
@Controller
public class ViewController {
    @GetMapping(value = {"/", "index"})
    public String index() {
        return "redirect:/queryDormitoryAnnouncement";
    }
    @GetMapping("/login.html")
    public String login() {
        return "login";
    }
}
