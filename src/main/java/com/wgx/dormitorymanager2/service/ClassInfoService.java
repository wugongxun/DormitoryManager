package com.wgx.dormitorymanager2.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wgx.dormitorymanager2.bean.ClassInfo;
import com.wgx.dormitorymanager2.bean.Student;
import com.wgx.dormitorymanager2.mapper.ClassInfoMapper;
import com.wgx.dormitorymanager2.message.Message;
import com.wgx.dormitorymanager2.utils.ExtractNumbers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * author:wgx
 * version:1.0
 */
@Service
public class ClassInfoService {
    @Autowired
    private ClassInfoMapper classInfoMapper;

    public ClassInfo queryClassInfoById(Integer classId) {
        return classInfoMapper.selectById(classId);
    }

    public Message queryAllClassByMajorId(Integer majorId) {
        QueryWrapper<ClassInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("major_id", majorId);
        List<ClassInfo> classInfoList = classInfoMapper.selectList(wrapper);
        if (classInfoList == null) {
            return Message.fail("没有班级", null);
        } else {
            HashMap<Object, Object> map = new HashMap<>();
            map.put("classInfoList", classInfoList);
            return Message.success("成功", map);
        }
    }

    public void insertClass(String className, Integer majorId, Integer academyId) {
        Integer classIdSuffix = ExtractNumbers.extractNumbers(className);
        Integer classId = majorId * 10000 + classIdSuffix;
        classInfoMapper.insert(new ClassInfo(classId, className, majorId, academyId));
    }
}
