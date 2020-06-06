package cn.wqz.study.springboot.druid.service;

import cn.wqz.study.springboot.druid.entity.Student;

public interface StudentService {
    int add(Student student);
    int update(Student student);
    int deleteBysno(String sno);
    Student queryStudentBySno(String sno);
}