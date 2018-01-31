package mumsched.controller;

import mumsched.AjaxResponse;
import mumsched.entity.Course;
import mumsched.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
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
    private CourseService courseService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        List<Course> courses = courseService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("courses", courses);
        modelAndView.setViewName("course/index");
        return modelAndView;
    }

    @RequestMapping(value = "/read/{id}", method = RequestMethod.GET)
    public String read(@PathVariable(value = "id") Long id, Model model) {

        Course course = courseService.findOne(id);
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
        model.addAttribute("courses", courseService.findAll());

        return "course/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute Course course, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("course", course);
            model.addAttribute("courses", courseService.findAll());
            return "course/create";
        }

        courseService.save(course);

        return "redirect:/course/";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable(value = "id") Long id, Model model) {

        Course course = courseService.findOne(id);
        if (course == null) {
            // not found
            return "404";
        }
        model.addAttribute("course", course);
        model.addAttribute("courses", courseService.findAllExcept(course.getId()));

        return "course/update";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable(value = "id") Long id,
                         @Valid @ModelAttribute("course") Course course,
                         BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("courses", courseService.findAllExcept(course.getId()));
            model.addAttribute("course", course);
            model.addAttribute("errors", result.getAllErrors());
            return "course/update";
        }
        Course c = courseService.findOne(id);
        c.setCode(course.getCode());
        c.setName(course.getName());
        // c.setDescription(course.getDescription());
        c.setPreRequisite(course.getPreRequisite());
        courseService.save(c);

        return "redirect:/course/";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody AjaxResponse delete(@PathVariable(value = "id") Long id) {
        Course course = courseService.findOne(id);
        AjaxResponse response = new AjaxResponse();
        if (course != null) {
            try {
                courseService.delete(id);
                response.success = true;
                response.msg = "Successfully deleted.";
            } catch (DataIntegrityViolationException ignore) {
                // Cannot remove course that is prerequisite of other course.
                response.success = false;
                response.msg = "Cannot remove course that is prerequisite of other course.";
            }
        }
        return response;
    }

}
