package com.wgx.dormitorymanager2.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wgx.dormitorymanager2.bean.DormitoryInfo;
import com.wgx.dormitorymanager2.bean.PaymentInfo;
import com.wgx.dormitorymanager2.bean.PaymentRecord;
import com.wgx.dormitorymanager2.message.Message;
import com.wgx.dormitorymanager2.service.DormitoryInfoService;
import com.wgx.dormitorymanager2.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

/**
 * author:wgx
 * version:1.0
 */
@Controller
public class PaymentInfoController {
    @Autowired
    private PaymentInfoService paymentInfoService;
    @Autowired
    private DormitoryInfoService dormitoryInfoService;

    @GetMapping("/queryPaymentInfoAndDormitoryInfoByDormitoryId")
    public ModelAndView queryPaymentInfoAndDormitoryInfoByDormitoryId(@RequestParam("dormitoryId") Integer dormitoryId) {
        PaymentInfo paymentInfo = paymentInfoService.queryPaymentInfoByDormitoryId(dormitoryId);
        DormitoryInfo dormitoryInfo = dormitoryInfoService.queryDormitoryById(dormitoryId);
        ModelAndView mav = new ModelAndView();
        mav.addObject("paymentInfo", paymentInfo);
        mav.addObject("dormitoryInfo", dormitoryInfo);
        mav.setViewName("forward:/paymentInformation");
        return mav;
    }

    @GetMapping("/queryPaymentRecordsByDormitoryId")
    @ResponseBody
    public Message queryPaymentRecordsByDormitoryId(@RequestParam("dormitoryId") Integer dormitoryId, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<PaymentRecord> paymentRecordsPage = paymentInfoService.queryPaymentRecordsByDormitoryId(dormitoryId, pageNum);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("paymentRecordsPage", paymentRecordsPage);
        return Message.success("成功", map);
    }
}
