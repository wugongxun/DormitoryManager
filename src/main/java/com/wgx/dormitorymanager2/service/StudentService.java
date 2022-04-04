package com.wgx.dormitorymanager2.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wgx.dormitorymanager2.bean.Academy;
import com.wgx.dormitorymanager2.bean.DormitoryInfo;
import com.wgx.dormitorymanager2.bean.Student;
import com.wgx.dormitorymanager2.mapper.AcademyMapper;
import com.wgx.dormitorymanager2.mapper.MajorMapper;
import com.wgx.dormitorymanager2.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:wgx
 * version:1.0
 */
@Service
public class StudentService {
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    MajorMapper majorMapper;
    @Autowired
    AcademyMapper academyMapper;

    public Academy queryAcademyByStudent(Student student) {
        Academy academy = academyMapper.selectById(student.getAcademyId());
        return academy;
    }
    public Boolean modifyPassword(Student student) {
        int i = studentMapper.updateById(student);
        return i > 0 ? true : false;
    }
    public List<Student> queryAllStudentsWithDormitoryNameByClassId(Integer classId) {
        return studentMapper.queryStudentsWithDormitoryNameByClassId(classId);
    }
    public void saveNewStudent(Student student) {
        studentMapper.insert(student);
    }
    public Boolean deleteStudentById(Integer studentId) {
        int i = studentMapper.deleteById(studentId);
        if (i > 0) {
            return true;
        } else {
            return false;
        }
    }
    public Student getStudentById(Integer studentId) {
        return studentMapper.selectById(studentId);
    }
    public void updateStudent(Student student) {
        studentMapper.updateById(student);
    }
    public List<Student> queryAllStudentByDormitoryId(Integer dormitoryId) {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("dormitory_id", dormitoryId);
        return studentMapper.selectList(wrapper);
    }
}
