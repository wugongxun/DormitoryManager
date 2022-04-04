package com.wgx.dormitorymanager2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wgx.dormitorymanager2.bean.PaymentRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaymentRecordMapper extends BaseMapper<PaymentRecord> {
    List<PaymentRecord> queryAllPaymentRecordByDormitoryId(Integer dormitoryId);
}
