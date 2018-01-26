package mumsched.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import mumsched.repository.StudentRepository;
import mumsched.entity.Student;

@Controller
@RequestMapping(path="/student")
public class StudentController {
    @Autowired
    private StudentRepository studentRepo;

    @RequestMapping(value="/", method=RequestMethod.GET)
    public ModelAndView index() {
        List<Student> students = studentRepo.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("students", students);
        modelAndView.setViewName("student/index");
        return modelAndView;
    }

}