package com.wgx.dormitorymanager2.service;


import com.wgx.dormitorymanager2.bean.Administrator;
import com.wgx.dormitorymanager2.mapper.AdministratorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:wgx
 * version:1.0
 */
@Service
public class AdministratorService {
    @Autowired
    AdministratorMapper administratorMapper;

    public Boolean modifyPassword(Administrator administrator) {
        int i = administratorMapper.updateById(administrator);
        return i > 0 ? true : false;
    }
}
