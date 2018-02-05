package mumsched.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import mumsched.service.StudentService;
import mumsched.entity.Entry;
import mumsched.entity.Student;

@Controller
@RequestMapping(path="/student")
public class StudentController {
    @Autowired
    private StudentService studentServ;

    @RequestMapping(value="/", method=RequestMethod.GET)
    public ModelAndView index() {
        List<Student> students = studentServ.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("students", students);
        modelAndView.setViewName("student/index");
        return modelAndView;
    }
    
    @RequestMapping(value = "/read/{id}", method = RequestMethod.GET)
    public String read(@PathVariable(value = "id") Long id, Model model) {

        Student student = studentServ.findOne(id);
        if (student == null) {
            // not found
            return "404";
        }
        model.addAttribute("student", student);

        return "student/read";
    }
    
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newStudent(Model model) {
        model.addAttribute("student", new Student());

        return "student/create";
    }
}