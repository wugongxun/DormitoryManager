package com.wgx.dormitorymanager2.service;

import com.wgx.dormitorymanager2.bean.PaymentInfo;
import com.wgx.dormitorymanager2.mapper.PaymentInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:wgx
 * version:1.0
 */
@Service
public class PaymentInfoService {
    @Autowired
    private PaymentInfoMapper paymentInfoMapper;

    public PaymentInfo queryPaymentInfoByDormitoryId(Integer dormitoryId) {
        return paymentInfoMapper.queryPaymentInfoByDormitoryId(dormitoryId);
    }
}
