package mumsched.controller;

import mumsched.entity.Course;
import mumsched.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path = "/course")
public class CourseController {
    @Autowired
    private CourseRepository courseRepo;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        List<Course> courses = courseRepo.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("courses", courses);
        modelAndView.setViewName("course/index");
        return modelAndView;
    }

    @RequestMapping(value = "/read/{id}", method = RequestMethod.GET)
    public String read(@PathVariable(value = "id") Long id, Model model) {

        Course course = courseRepo.findOne(id);
        if (course == null) {
            // not found
            return "404";
        }
        model.addAttribute("course", course);

        return "course/read";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newCourse(Model model) {
        model.addAttribute("course", new Course());

        return "course/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute Course course, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("course", course);
            return "course/create";
        }

        courseRepo.save(course);

        return "redirect:/course/";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable(value = "id") Long id, Model model) {

        Course course = courseRepo.findOne(id);
        if (course == null) {
            // not found
            return "404";
        }
        model.addAttribute("course", course);

        return "course/update";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable(value = "id") Long id,
                         @Valid @ModelAttribute("course") Course course,
                         BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("course", course);
            model.addAttribute("errors", result.getAllErrors());
            return "course/update";
        }
        course = courseRepo.save(course);

        return "redirect:/course/";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String delete(@PathVariable(value = "id") Long id) {
        Course course = courseRepo.findOne(id);
        if (course != null) {
            courseRepo.delete(course);
        }
        return "redirect:/course/";
    }

}
