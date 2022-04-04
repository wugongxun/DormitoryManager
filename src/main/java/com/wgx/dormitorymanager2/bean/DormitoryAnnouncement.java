package com.wgx.dormitorymanager2.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * author:wgx
 * version:1.0
 */
@Data
@TableName("dormitory_announcement")
public class DormitoryAnnouncement {
    @TableId(type = IdType.AUTO)
    private Integer announcementId;
    private String title;
    private String content;
    private String publisher;
    private String releaseDate;
    private String expirationDate;
}
