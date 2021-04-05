package com.huzi.service;

import com.huzi.dao.StudentDao;
import com.huzi.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentDao studentDao;

    @Override
    public int insertStudent(Student student) {
        return studentDao.addStudent(student);

    }
}
