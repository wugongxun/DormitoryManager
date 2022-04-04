package com.wgx.dormitorymanager2.controller;

import com.wgx.dormitorymanager2.bean.Academy;
import com.wgx.dormitorymanager2.bean.Administrator;
import com.wgx.dormitorymanager2.bean.Student;
import com.wgx.dormitorymanager2.message.Message;
import com.wgx.dormitorymanager2.service.AdministratorService;
import com.wgx.dormitorymanager2.service.LoginService;
import com.wgx.dormitorymanager2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * author:wgx
 * version:1.0
 */
@Controller
public class LoginController {
    @Autowired
    LoginService loginService;
    @Autowired
    StudentService studentService;
    @Autowired
    AdministratorService administratorService;

    //登录
    @PostMapping("/login")
    @ResponseBody
    public Message login(@RequestParam("account") String account, @RequestParam("password") String password, @RequestParam("loginType") String loginType, HttpSession session) {
        //登录前先执行登出方法，防止同时登录多个账号
        logout(session);
        Message loginMessage = loginService.login(account, password, loginType);
        if (loginMessage.getStatusCode() == 100) {
            if ("student".equals(loginType)) {
                Academy academy = studentService.queryAcademyByStudent((Student) loginMessage.getAttributes().get("student"));
                session.setAttribute("student", loginMessage.getAttributes().get("student"));
                session.setAttribute("academy", academy);
            }else {
                session.setAttribute("administrator", loginMessage.getAttributes().get("administrator"));
                session.setAttribute("unit", "管理员");
            }
            return loginMessage;
        }else if(loginMessage.getStatusCode() == 200) {
            return loginMessage;
        }
        return Message.fail("未知错误", null);
    }
    //登出
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        loginService.logout(session);
        return "redirect:/";
    }
    //修改密码
    @GetMapping("/modifyPassword")
    @ResponseBody
    public Message modifyPassword(HttpSession session, @RequestParam("newPassword") String newPassword, @RequestParam("oldPassword") String oldPassword) {
        if (newPassword.length() > 16 || newPassword.length() < 5) {
            return Message.fail("密码必须为5-16位", null);
        }
        Student student = (Student) session.getAttribute("student");
        Administrator administrator = (Administrator) session.getAttribute("administrator");
        if (student != null) {
            if (!(student.getPassword().equals(oldPassword))) {
                return Message.fail("原密码错误", null);
            }
            if (student.getPassword().equals(newPassword)) {
                return Message.fail("新旧密码相同", null);
            }
            student.setPassword(newPassword);
            studentService.modifyPassword(student);
            return Message.success("修改成功", null);
        }else if (administrator != null) {
            if (!(administrator.getPassword().equals(oldPassword))) {
                return Message.fail("原密码错误", null);
            }
            if (administrator.getPassword().equals(newPassword)) {
                return Message.fail("新旧密码相同", null);
            }
            administrator.setPassword(newPassword);
            administratorService.modifyPassword(administrator);
            return Message.success("修改成功", null);
        }
        return Message.fail("修改失败", null);
    }
}
