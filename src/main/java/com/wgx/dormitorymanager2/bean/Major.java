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
@TableName("major_info")
@AllArgsConstructor
@NoArgsConstructor
public class Major {
    @TableId
    private Integer majorId;
    private String majorName;
    private Integer academyId;
}
