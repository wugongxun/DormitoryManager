package com.wgx.dormitorymanager2.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wgx.dormitorymanager2.bean.Administrator;
import com.wgx.dormitorymanager2.bean.RepairInfo;
import com.wgx.dormitorymanager2.bean.Student;
import com.wgx.dormitorymanager2.message.Message;
import com.wgx.dormitorymanager2.service.RepairInfoService;
import javafx.beans.binding.MapExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * author:wgx
 * version:1.0
 */
@Controller
public class RepairInfoController {
    @Autowired
    private RepairInfoService repairInfoService;

    @GetMapping("/repairFeedback")
    public ModelAndView repairFeedback(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        ModelAndView mav = new ModelAndView();
        Student student = (Student) session.getAttribute("student");
        if (student != null) {
            List<RepairInfo> repairInfos = repairInfoService.queryAllRepairInfoByDormitory(student.getDormitoryId());
            mav.addObject("repairInfos", repairInfos);
            mav.setViewName("repairFeedback_student");
            return mav;
        }
        Administrator administrator = (Administrator) session.getAttribute("administrator");
        if (administrator != null) {
            Page<RepairInfo> repairInfosPage = repairInfoService.queryAllRepairInfoAndDormitoryByPage(pageNum);
            mav.addObject("repairInfosPage", repairInfosPage);
            mav.setViewName("repairFeedback_administrator");
            return mav;
        }
        mav.addObject("exception", new RuntimeException("有没检查到登录用户"));
        mav.setViewName("error/error");
        return mav;
    }

    @GetMapping("/urge")
    @ResponseBody
    public Message urge(@RequestParam("repairId") Integer repairId) {
        RepairInfo repairInfo = repairInfoService.queryRepairInfoById(repairId);
        String status = repairInfo.getStatus();
        if ("已处理".equals(status) || "被撤回".equals(status)) {
            return Message.fail("该报修" + status, null);
        }
        if (repairInfo.getSituation() == 2) {
            return Message.success("催促成功", null);
        }
        repairInfoService.urge(repairInfo);
        return Message.success("催促成功", null);
    }

    @GetMapping("/uploadRepair")
    public String uploadRepair(@RequestParam("repairItem") String repairItem,
                               @RequestParam("detailedDescription") String detailedDescription,
                               @RequestParam("situation") Integer situation,
                               HttpSession session) {
        if (detailedDescription.length() > 30) {
            detailedDescription = detailedDescription.substring(0, 30);
        }
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String repairDate = sdf.format(now);
        Integer repairId = repairInfoService.queryLastRepairId(repairDate) + 1;
        Student student = (Student) session.getAttribute("student");
        Integer dormitoryId = student.getDormitoryId();
        RepairInfo repairInfo = new RepairInfo(repairId, dormitoryId, repairDate, repairItem, detailedDescription, situation, "未处理", null);
        repairInfoService.saveRepairInfo(repairInfo);
        return "forward:/repairFeedback";
    }
}
