package com.wgx.dormitorymanager2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wgx.dormitorymanager2.bean.Major;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MajorMapper extends BaseMapper<Major> {
    Integer findMaxMajorIdInAcademy(Integer academyId);
}
