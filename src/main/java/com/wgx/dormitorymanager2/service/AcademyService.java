package com.wgx.dormitorymanager2.service;

import com.wgx.dormitorymanager2.bean.Academy;
import com.wgx.dormitorymanager2.mapper.AcademyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:wgx
 * version:1.0
 */
@Service
public class AcademyService {
    @Autowired
    private AcademyMapper academyMapper;

    public List<Academy> queryAllAcademy() {
        return academyMapper.selectList(null);
    }

    /**
     * 插入一个新的学院
     * @param academyName
     * @return 插入后的学院的id
     */
    public Integer InsertAcademy(String academyName) {
        int academyId = findMaxAcademyId() + 1000;
        academyMapper.insert(new Academy(academyId, academyName));
        return academyId;
    }

    /**
     * 查询最大的学院id
     * @return
     */
    public Integer findMaxAcademyId() {
        return academyMapper.findMaxAcademyId();
    }
}
