package com.wgx.dormitorymanager2.controller;

import com.wgx.dormitorymanager2.message.Message;
import com.wgx.dormitorymanager2.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * author:wgx
 * version:1.0
 */
@Controller
public class MajorController {

    @Autowired
    private MajorService majorService;

    @PostMapping("/queryAllMajorByAcademyId")
    @ResponseBody
    public Message queryAllMajorByAcademyId(@RequestParam("academyId") Integer academyId) {
        return majorService.queryAllMajorByAcademyId(academyId);
    }
}
