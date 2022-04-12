package com.wgx.dormitorymanager2.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wgx.dormitorymanager2.bean.Administrator;
import com.wgx.dormitorymanager2.bean.DormitoryAnnouncement;
import com.wgx.dormitorymanager2.message.Message;
import com.wgx.dormitorymanager2.service.DormitoryAnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

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

    @GetMapping("/dormitoryAnnouncement")
    public ModelAndView dormitoryAnnouncement() {
        List<DormitoryAnnouncement> dormitoryAnnouncements = dormitoryAnnouncementService.dormitoryAnnouncement();
        ModelAndView mav = new ModelAndView();
        mav.addObject("dormitoryAnnouncements", dormitoryAnnouncements);
        mav.setViewName("dormitoryAnnouncement");
        return mav;
    }

    @GetMapping("/releaseAnnouncement")
    public String releaseAnnouncement(@RequestParam("title") String title,
                                      @RequestParam("content") String content,
                                      @RequestParam("expirationDate") String expirationDate,
                                      HttpSession session) {
        Administrator administrator = (Administrator) session.getAttribute("administrator");
        String publisher = administrator.getAdministratorName();//发布者
        LocalDate now = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String releaseDate = dateTimeFormatter.format(now);//发布时间
        Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}");
        boolean matches = pattern.matcher(expirationDate).matches();
        if (!matches) {
            int months = 0;//需要加上的月数
            switch (expirationDate) {
                case "oneMonth":
                    months = 1;
                    break;
                case "threeMonth":
                    months = 3;
                    break;
                case "sixMonth":
                    months = 6;
                    break;
                case "oneYear":
                    months = 12;
                    break;
            }
            LocalDate localDate = now.plusMonths(months);
            expirationDate = dateTimeFormatter.format(localDate);
        }
        DormitoryAnnouncement dormitoryAnnouncement = new DormitoryAnnouncement(null, title, content, publisher, releaseDate, expirationDate);
        dormitoryAnnouncementService.releaseAnnouncement(dormitoryAnnouncement);
        return "redirect:/dormitoryAnnouncement";
    }

    @GetMapping("/deleteAnnouncement")
    @ResponseBody
    public Message deleteAnnouncement(@RequestParam("announcementId") Integer announcementId) {
        dormitoryAnnouncementService.deleteAnnouncement(announcementId);
        return Message.success("成功", null);
    }

    @GetMapping("/queryDormitoryAnnouncementPageByAnnouncementId")
    public ModelAndView queryDormitoryAnnouncementPageByAnnouncementId(@RequestParam("announcementId") Integer announcementId) {
        Page<DormitoryAnnouncement> dormitoryAnnouncementPage = dormitoryAnnouncementService.queryDormitoryAnnouncementPageByAnnouncementId(announcementId);
        ModelAndView mav = new ModelAndView();
        mav.addObject("dormitoryAnnouncementPage", dormitoryAnnouncementPage);
        mav.setViewName("showAnnouncement");
        return mav;
    }
    @GetMapping("showDormitoryAnnouncement")
    public ModelAndView showDormitoryAnnouncement(@RequestParam("pageNum") Integer pageNum) {
        Page<DormitoryAnnouncement> dormitoryAnnouncementPage = dormitoryAnnouncementService.queryDormitoryAnnouncementPage(pageNum);
        ModelAndView mav = new ModelAndView();
        mav.addObject("dormitoryAnnouncementPage", dormitoryAnnouncementPage);
        mav.setViewName("showAnnouncement");
        return mav;
    }
    @GetMapping("/queryDormitoryAnnouncementById")
    @ResponseBody
    public Message queryDormitoryAnnouncementById(@RequestParam("announcementId") Integer announcementId) {
        DormitoryAnnouncement dormitoryAnnouncement = dormitoryAnnouncementService.queryDormitoryAnnouncementById(announcementId);
        if (dormitoryAnnouncement != null) {
            HashMap<Object, Object> map = new HashMap<>();
            map.put("dormitoryAnnouncement", dormitoryAnnouncement);
            return Message.success("成功", map);
        } else {
            return Message.fail("未查询到宿舍公告", null);
        }
    }
    @GetMapping("/changeAnnouncement")
    public String changeAnnouncement(DormitoryAnnouncement dormitoryAnnouncement, HttpSession session) {
        Administrator administrator = (Administrator) session.getAttribute("administrator");
        dormitoryAnnouncement.setPublisher(administrator.getAdministratorName());
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String releaseDate = sdf.format(now);
        dormitoryAnnouncement.setReleaseDate(releaseDate);
        dormitoryAnnouncementService.changeAnnouncement(dormitoryAnnouncement);
        return "redirect:/dormitoryAnnouncement";
    }
}
