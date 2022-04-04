package com.wgx.dormitorymanager2.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wgx.dormitorymanager2.bean.Major;
import com.wgx.dormitorymanager2.mapper.MajorMapper;
import com.wgx.dormitorymanager2.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * author:wgx
 * version:1.0
 */
@Service
public class MajorService {
    @Autowired
    private MajorMapper majorMapper;

    public Message queryAllMajorByAcademyId(Integer academyId) {
        QueryWrapper<Major> wrapper = new QueryWrapper<>();
        wrapper.eq("academy_id", academyId);
        List<Major> majorList = majorMapper.selectList(wrapper);
        if (majorList == null) {
            return Message.fail("没有专业", null);
        }else {
            HashMap<Object, Object> map = new HashMap<>();
            map.put("majorList", majorList);
            return Message.success("成功", map);
        }
    }

    /**
     * 在一个学院下插入一个新专业
     * @param majorName
     * @param academyId
     * @return 插入后的专业id
     */
    public Integer insertMajor(String majorName, Integer academyId) {
        int majorId = findMaxMajorIdInAcademy(academyId) + 1;
        majorMapper.insert(new Major(majorId, majorName, academyId));
        return majorId;
    }

    /**
     * 查询在一个学院下的最大的majorId
     * @param academyId
     * @return 如果该学院下没有专业，返回学院id
     */
    public Integer findMaxMajorIdInAcademy(Integer academyId) {
        Integer majorId = majorMapper.findMaxMajorIdInAcademy(academyId);
        if (majorId == null) {
            return academyId;
        } else {
            return majorId;
        }
    }
}
