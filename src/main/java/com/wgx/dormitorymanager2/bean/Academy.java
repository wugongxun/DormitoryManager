package com.wgx.dormitorymanager2.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author:wgx
 * version:1.0
 */
@Data
@TableName("academy_info")
@AllArgsConstructor
@NoArgsConstructor
public class Academy {
    @TableId
    private Integer academyId;
    private String academyName;
}
