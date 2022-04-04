package com.wgx.dormitorymanager2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wgx.dormitorymanager2.bean.PaymentInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentInfoMapper extends BaseMapper<PaymentInfo> {
    PaymentInfo queryPaymentInfoByDormitoryId(Integer dormitoryId);
}
