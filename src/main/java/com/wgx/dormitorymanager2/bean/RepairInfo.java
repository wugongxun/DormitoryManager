package com.wgx.dormitorymanager2.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * author:wgx
 * version:1.0
 */
@Data
@TableName("repair_information")
@AllArgsConstructor
@NoArgsConstructor
public class RepairInfo {
    @TableId
    private Integer repairId;
    @TableField("repair_dormitory")
    private Integer repairDormitoryId;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String repairDate;
    private String repairItem;
    private String detailedDescription;
    private Integer situation;
    private String status;
    @TableField(exist = false)
    private DormitoryInfo repairDormitory;
}
