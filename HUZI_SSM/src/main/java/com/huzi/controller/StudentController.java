package com.huzi.controller;

import com.huzi.domain.Student;
import com.huzi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @RequestMapping("/addStudent.do")
    public ModelAndView addStudent(Student student){

        ModelAndView mv = new ModelAndView();
        String result = "失败";
        int a = studentService.insertStudent(student);
        if( a > 0){
            result="成功";
        }
        mv.addObject("result",result);
        mv.setViewName("result");
        return mv;
    }
}
