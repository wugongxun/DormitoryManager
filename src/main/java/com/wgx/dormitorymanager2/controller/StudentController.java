package com.wgx.dormitorymanager2.controller;

import com.wgx.dormitorymanager2.bean.Academy;
import com.wgx.dormitorymanager2.bean.ClassInfo;
import com.wgx.dormitorymanager2.bean.Student;
import com.wgx.dormitorymanager2.message.Message;
import com.wgx.dormitorymanager2.service.AcademyService;
import com.wgx.dormitorymanager2.service.ClassInfoService;
import com.wgx.dormitorymanager2.service.DormitoryInfoService;
import com.wgx.dormitorymanager2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;

/**
 * author:wgx
 * version:1.0
 */
@Controller
public class StudentController {
    @Autowired
    private ClassInfoService classInfoService;
    @Autowired
    private DormitoryInfoService dormitoryInfoService;
    @Autowired
    private AcademyService academyService;
    @Autowired
    private StudentService studentService;

    @GetMapping("/personalCenter")
    public String personalCenter(HttpSession session, Model model) {
        Student student = (Student) session.getAttribute("student");
        if (student != null) {
            model.addAttribute("classInfo", classInfoService.queryClassInfoById(student.getClassId()));
            model.addAttribute("dormitoryInfo", dormitoryInfoService.queryDormitoryById(student.getDormitoryId()));
        }
        return "personalCenter";
    }
    @RequestMapping("/studentManage")
    public ModelAndView studentManage() {
        ModelAndView mav = new ModelAndView();
        List<Academy> academies = academyService.queryAllAcademy();
        mav.addObject("academies", academies);
        mav.setViewName("studentManage");
        return mav;
    }
    @GetMapping("/queryAllStudentsWithDormitoryNameByClassId")
    public String queryAllStudentsWithDormitoryNameByClassId(@RequestParam("classId") Integer classId, Model model) {
        List<Student> students = studentService.queryAllStudentsWithDormitoryNameByClassId(classId);
        ClassInfo classInfo = classInfoService.queryClassInfoById(classId);
        model.addAttribute("students", students);
        model.addAttribute("classInfo", classInfo);
        return "forward:/studentManage";
    }

    @PostMapping("/student")
    public String saveNewStudent(Student student) {
        studentService.saveNewStudent(student);
        return "redirect:/studentManage";
    }

    @DeleteMapping("/student/{studentId}")
    @ResponseBody
    public Message deleteStudentById(@PathVariable("studentId") Integer studentId) {
        Boolean aBoolean = studentService.deleteStudentById(studentId);
        if (aBoolean) {
            return Message.success("删除成功", null);
        } else {
            return Message.fail("删除失败", null);
        }
    }
    @GetMapping("/student/{studentId}")
    @ResponseBody
    public Message getStudentById(@PathVariable("studentId") Integer studentId, Model model) {
        Student student = studentService.getStudentById(studentId);
        if (student == null) {
            return Message.fail("未查询到学生", null);
        } else {
            model.addAttribute("student", student);
            HashMap<Object, Object> map = new HashMap<>();
            map.put("student", student);
            return Message.success("成功", map);
        }
    }
    @PutMapping("/student")
    public String updateStudent(Student student) {
        studentService.updateStudent(student);
        return "redirect:/studentManage";
    }
}
