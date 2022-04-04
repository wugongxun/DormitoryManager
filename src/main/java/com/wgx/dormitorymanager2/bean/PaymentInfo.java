package com.wgx.dormitorymanager2.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * author:wgx
 * version:1.0
 */
@Data
@TableName("payment_Info")
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInfo {
    @TableId
    private Integer dormitoryId;
    private Integer airConditioningFees;
    private Integer electricityFees;
    @TableField(exist = false)
    private List<PaymentRecord> paymentRecords;
}
