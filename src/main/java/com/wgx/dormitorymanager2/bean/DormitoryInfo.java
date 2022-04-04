package com.wgx.dormitorymanager2.bean;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("dormitory_info")
@AllArgsConstructor
@NoArgsConstructor
public class DormitoryInfo {
    @TableId
    private Integer dormitoryId;
    private Integer buildingId;
    private Integer floorId;
    private String photoName;
    @TableField(exist = false)
    private ScoreAndLikesInfo scoreAndLikesInfo;
}
