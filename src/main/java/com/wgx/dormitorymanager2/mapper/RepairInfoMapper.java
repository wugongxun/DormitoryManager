package com.wgx.dormitorymanager2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wgx.dormitorymanager2.bean.RepairInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * author:wgx
 * version:1.0
 */
@Mapper
public interface RepairInfoMapper extends BaseMapper<RepairInfo> {
    List<RepairInfo> queryAllRepairInfoAndDormitory(String sortType, Boolean showOnlyUnprocessed);
}
