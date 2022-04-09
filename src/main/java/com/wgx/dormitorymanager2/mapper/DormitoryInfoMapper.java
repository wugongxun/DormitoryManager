package com.wgx.dormitorymanager2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wgx.dormitorymanager2.bean.DormitoryInfo;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;
import java.util.List;

@Mapper
public interface DormitoryInfoMapper extends BaseMapper<DormitoryInfo> {
    List<Integer> queryAllBuildingId();

    @Override
    DormitoryInfo selectById(Serializable id);
}
