package com.wgx.dormitorymanager2.controller;

import com.wgx.dormitorymanager2.message.Message;
import com.wgx.dormitorymanager2.service.AcademyService;
import com.wgx.dormitorymanager2.service.ClassInfoService;
import com.wgx.dormitorymanager2.service.MajorService;
import com.wgx.dormitorymanager2.utils.IsNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;

/**
 * author:wgx
 * version:1.0
 */
@Controller
public class ClassInfoController {
    @Autowired
    private ClassInfoService classInfoService;
    @Autowired
    private AcademyService academyService;
    @Autowired
    private MajorService majorService;

    @PostMapping("/queryAllClassByMajorId")
    @ResponseBody
    public Message queryAllClassByMajorId(@RequestParam("majorId") Integer majorId) {
        return classInfoService.queryAllClassByMajorId(majorId);
    }
    @PostMapping("/addNewClassWhitAcademyAndMajor")
    @ResponseBody
    public Message addNewClassWhitAcademyAndMajor(@RequestParam("newAcademy") String newAcademy, @RequestParam("newMajor") String newMajor, @RequestParam("newClass") String newClass) {
        if (!IsNumber.StringIsNumber(newAcademy)) {
            Integer academyId = academyService.InsertAcademy(newAcademy);
            Integer majorId = majorService.insertMajor(newMajor, academyId);
            classInfoService.insertClass(newClass, majorId, academyId);
            return Message.success("添加成功", null);
        } else if (!IsNumber.StringIsNumber(newMajor)) {
            Integer academyId = Integer.valueOf(newAcademy);
            Integer majorId = majorService.insertMajor(newMajor, academyId);
            classInfoService.insertClass(newClass, majorId, academyId);
            return Message.success("添加成功", null);
        } else if (!IsNumber.StringIsNumber(newClass)) {
            Integer academyId = Integer.valueOf(newAcademy);
            Integer majorId = Integer.valueOf(newMajor);
            classInfoService.insertClass(newClass, majorId, academyId);
            return Message.success("成功", null);
        }
        return Message.fail("班级已存在", null);
    }
}
