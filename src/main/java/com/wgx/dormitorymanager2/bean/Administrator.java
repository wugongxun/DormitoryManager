package com.wgx.dormitorymanager2.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * author:wgx
 * version:1.0
 */
@Data
@TableName("administrators")
public class Administrator {
    @TableId
    private Integer administratorId;
    private String administratorName;
    private String password;
}
