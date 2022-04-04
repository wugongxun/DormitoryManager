package com.wgx.dormitorymanager2.controller;

import com.wgx.dormitorymanager2.bean.DormitoryInfo;
import com.wgx.dormitorymanager2.bean.PaymentInfo;
import com.wgx.dormitorymanager2.service.DormitoryInfoService;
import com.wgx.dormitorymanager2.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
}
