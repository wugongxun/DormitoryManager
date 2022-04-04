package com.wgx.dormitorymanager2.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author:wgx
 * version:1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreAndLikesInfo {
    private Integer score;
    private Integer likes;
    private Boolean currentIsLike;
}
