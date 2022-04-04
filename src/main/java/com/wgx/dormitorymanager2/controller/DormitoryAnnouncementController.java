package com.wgx.dormitorymanager2.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wgx.dormitorymanager2.bean.DormitoryAnnouncement;
import com.wgx.dormitorymanager2.service.DormitoryAnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * author:wgx
 * version:1.0
 */
@Controller
public class DormitoryAnnouncementController {
    @Autowired
    DormitoryAnnouncementService dormitoryAnnouncementService;

    @GetMapping("/queryDormitoryAnnouncement")
    public String queryDormitoryAnnouncement(Model model, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) throws ParseException {
        Page<DormitoryAnnouncement> dormitoryAnnouncementPage = dormitoryAnnouncementService.queryDormitoryAnnouncementNotExpired(pageNum);
        model.addAttribute("dormitoryAnnouncementPage", dormitoryAnnouncementPage);
        return "index";
    }
}
