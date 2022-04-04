package com.wgx.dormitorymanager2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wgx.dormitorymanager2.bean.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    List<Student> queryStudentsWithDormitoryNameByClassId(Integer classId);
}
