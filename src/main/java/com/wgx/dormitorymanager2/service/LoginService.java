package com.wgx.dormitorymanager2.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wgx.dormitorymanager2.bean.Administrator;
import com.wgx.dormitorymanager2.bean.Student;
import com.wgx.dormitorymanager2.mapper.AdministratorMapper;
import com.wgx.dormitorymanager2.mapper.StudentMapper;
import com.wgx.dormitorymanager2.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * author:wgx
 * version:1.0
 */
@Service
public class LoginService {
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    AdministratorMapper administratorMapper;

    public Message login(String account, String password, String loginType) {
        //判断loginType，分别操作
        if ("student".equals(loginType)) {
            try {
                //可能出现字符串转数字，异常，不用处理，直接false
                Integer studentId = Integer.valueOf(account);
                Student student = studentMapper.selectById(studentId);
                if (student == null) {
                    return Message.fail("账号或密码错误", null);
                }
                if (!(password.equals(student.getPassword()))) {
                    return Message.fail("账号或密码错误", null);
                }
                HashMap<Object, Object> map = new HashMap<>();
                map.put("student", student);
                return Message.success("登录成功", map);
            } catch (NumberFormatException e) {
                //只捕捉NumberFormatException异常，且不做处理
                return Message.fail("账号或密码错误", null);
            }
        }else if ("administrator".equals(loginType)) {
            String administratorName = account;
            QueryWrapper<Administrator> administratorQueryWrapper = new QueryWrapper<>();
            QueryWrapper<Administrator> wrapper = administratorQueryWrapper.eq("administrator_name", administratorName);
            Administrator administrator = administratorMapper.selectOne(wrapper);
            if (administrator == null) {
                return Message.fail("账号或密码错误", null);
            }
            if (!(password.equals(administrator.getPassword()))) {
                return Message.fail("账号或密码错误", null);
            }
            HashMap<Object, Object> map = new HashMap<>();
            map.put("administrator", administrator);
            return Message.success("登录成功", map);
        }
        return Message.fail("未知错误", null);
    }

    public Boolean logout(HttpSession session) {
        Object student = session.getAttribute("student");
        Object administrator = session.getAttribute("administrator");
        if (student == null && administrator == null) {
            return false;
        }
        session.removeAttribute("student");
        session.removeAttribute("administrator");
        return true;
    }
}
