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
@TableName("students_info")
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @TableId
    private Integer studentId;
    private String studentName;
    private String gender;
    private String address;
    private String idNumber;
    private String phoneNumber;
    private Integer academyId;
    private Integer majorId;
    private Integer classId;
    private Integer dormitoryId;
    private String grade;
    private String password;
    @TableField(exist = false)
    private Integer buildingId;
    @TableField(exist = false)
    private Integer floorId;
}
