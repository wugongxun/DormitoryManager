package com.wgx.dormitorymanager2.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wgx.dormitorymanager2.bean.RepairInfo;
import com.wgx.dormitorymanager2.mapper.RepairInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    /**
     * 查询所有RepairInfo和DormitoryInfo,并分页
     * @param pageNum 当前的页数
     * @param sortType 排序方式(报修日期或者紧急优先)
     * @return
     */
    public Page<RepairInfo> queryAllRepairInfoAndDormitoryByPage(Integer pageNum, String sortType, Boolean showOnlyUnprocessed) {
        List<RepairInfo> repairInfos = repairInfoMapper.queryAllRepairInfoAndDormitory(sortType, showOnlyUnprocessed);
        Page<RepairInfo> page = new Page<>(pageNum, 5);
        int total = repairInfos.size();
        int pages = 0;
        if (total % 5 == 0) {
            pages = total / 5;
        } else {
            pages = total / 5 + 1;
        }
        page.setTotal(total);
        page.setPages(pages);
        //如果当前页面为负数或0,返回第一页
        if (pageNum <= 0) {
            pageNum = 1;
        }
        //如果不为最后一页,正常返回
        if (pageNum < pages) {
            ArrayList<RepairInfo> records = new ArrayList<>();
            for (int i = (pageNum - 1) * 5; i < pageNum * 5; i++) {
                records.add(repairInfos.get(i));
            }
            page.setRecords(records);
        } else {//当前页面为最后一页,或者大于最后一页,也返回最后一页
            ArrayList<RepairInfo> records = new ArrayList<>();
            for (int i = (pageNum - 1) * 5; i < total; i++) {
                records.add(repairInfos.get(i));
            }
            page.setRecords(records);
        }
        return page;
    }

    public void finishProcessing(Integer repairId) {
        RepairInfo repairInfo = repairInfoMapper.selectById(repairId);
        if ("未处理".equals(repairInfo.getStatus()) || "稍后处理".equals(repairInfo.getStatus())) {
            repairInfo.setStatus("已完成");
            repairInfoMapper.updateById(repairInfo);
        }
    }

    public void laterProcessing(Integer repairId) {
        RepairInfo repairInfo = repairInfoMapper.selectById(repairId);
        if ("未处理".equals(repairInfo.getStatus())) {
            repairInfo.setStatus("稍后处理");
            repairInfoMapper.updateById(repairInfo);
        }
    }

    public void withdraw(Integer repairId) {
        RepairInfo repairInfo = repairInfoMapper.selectById(repairId);
        if ("未处理".equals(repairInfo.getStatus()) || "稍后处理".equals(repairInfo.getStatus())) {
            repairInfo.setStatus("被撤回");
            repairInfoMapper.updateById(repairInfo);
        }
    }
}
