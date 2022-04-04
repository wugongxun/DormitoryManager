package com.wgx.dormitorymanager2.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * author:wgx
 * version:1.0
 */
@Data
@TableName("class_info")
@AllArgsConstructor
@NoArgsConstructor
public class ClassInfo {
    @TableId
    private Integer classId;
    private String className;
    private Integer majorId;
    private Integer academyId;
}
