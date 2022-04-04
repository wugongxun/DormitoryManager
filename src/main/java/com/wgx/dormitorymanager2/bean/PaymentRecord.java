package com.wgx.dormitorymanager2.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.swing.*;
import java.util.Date;

/**
 * author:wgx
 * version:1.0
 */
@Data
@TableName("payment_records")
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRecord {
    private Integer dormitoryId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String paymentDate;
    private String type;
    private Integer paymentAmount;
    private String paymentAccount;
}
