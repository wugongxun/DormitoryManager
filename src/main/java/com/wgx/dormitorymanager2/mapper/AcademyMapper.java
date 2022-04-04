package com.wgx.dormitorymanager2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wgx.dormitorymanager2.bean.Academy;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AcademyMapper extends BaseMapper<Academy> {
    Integer findMaxAcademyId();
}
