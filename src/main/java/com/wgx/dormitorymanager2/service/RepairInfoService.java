package com.wgx.dormitorymanager2.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wgx.dormitorymanager2.bean.RepairInfo;
import com.wgx.dormitorymanager2.mapper.RepairInfoMapper;
import org.mockito.internal.matchers.ArrayEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:wgx
 * version:1.0
 */
@Service
public class RepairInfoService {
    @Autowired
    private RepairInfoMapper repairInfoMapper;

    public List<RepairInfo> queryAllRepairInfoByDormitory(Integer dormitoryId) {
        QueryWrapper<RepairInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("repair_dormitory", dormitoryId);
        return repairInfoMapper.selectList(wrapper);
    }

    public RepairInfo queryRepairInfoById(Integer repairId) {
        return repairInfoMapper.selectById(repairId);
    }

    public void urge(RepairInfo repairInfo) {
        repairInfo.setSituation(2);
        repairInfoMapper.updateById(repairInfo);
    }

    public Integer queryLastRepairId(String repairDate) {
        QueryWrapper<RepairInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("repair_date", repairDate).orderByDesc("repair_id");
        List<RepairInfo> repairInfos = repairInfoMapper.selectList(wrapper);
        if (repairInfos.isEmpty()) {
            repairDate += "00";
            return Integer.valueOf(repairDate.replaceAll("-", ""));
        }
        return repairInfos.stream().findFirst().get().getRepairId();
    }
    public void saveRepairInfo(RepairInfo repairInfo) {
        repairInfoMapper.insert(repairInfo);
    }
}
