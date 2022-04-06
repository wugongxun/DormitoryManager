package com.wgx.dormitorymanager2.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wgx.dormitorymanager2.bean.PaymentInfo;
import com.wgx.dormitorymanager2.bean.PaymentRecord;
import com.wgx.dormitorymanager2.mapper.PaymentInfoMapper;
import com.wgx.dormitorymanager2.mapper.PaymentRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.config.QuerydslWebConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:wgx
 * version:1.0
 */
@Service
public class PaymentInfoService {
    @Autowired
    private PaymentInfoMapper paymentInfoMapper;
    @Autowired
    private PaymentRecordMapper paymentRecordMapper;

    public PaymentInfo queryPaymentInfoByDormitoryId(Integer dormitoryId) {
        return paymentInfoMapper.queryPaymentInfoByDormitoryId(dormitoryId);
    }
    public Page<PaymentRecord> queryPaymentRecordsByDormitoryId(Integer dormitoryId, Integer pageNum) {
        Page<PaymentRecord> page = new Page<>(pageNum, 5);
        QueryWrapper<PaymentRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("dormitory_id", dormitoryId);
        wrapper.orderByAsc("payment_date");
        Page<PaymentRecord> paymentRecordsPage = paymentRecordMapper.selectPage(page, wrapper);
        return paymentRecordsPage;
    }
}
