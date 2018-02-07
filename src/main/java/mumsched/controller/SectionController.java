package mumsched.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import mumsched.AjaxResponse;
import mumsched.entity.Section;
import mumsched.service.BlockService;
import mumsched.service.CourseService;
import mumsched.service.FacultyService;
import mumsched.service.SectionService;

@Controller
@RequestMapping(path = "/section")
public class SectionController {
    @Autowired
    private BlockService blockService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private FacultyService facultyService;

    @RequestMapping(value="/", method=RequestMethod.GET)
    public ModelAndView index() {
        List<Section> sections = sectionService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("sections", sections);
        modelAndView.setViewName("section/index");
        return modelAndView;
    }

    @RequestMapping(value = "/read/{id}", method = RequestMethod.GET)
    public String read(@PathVariable(value="id") Long id, Model model) {
        Section section = sectionService.findOne(id);
        if(section == null) {
            // not found
            return "404";
        }
        model.addAttribute("section", section);

        return "section/read";
    }

    @RequestMapping(value="/new", method=RequestMethod.GET)
    public String newSection(Model model) {
        model.addAttribute("section", new Section());
        model.addAttribute("blocks", blockService.findAll());
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("faculties", facultyService.findAll());

        return "section/create";
    }

    @RequestMapping(value="/create", method=RequestMethod.POST)
    public String create(@Valid @ModelAttribute Section section, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("section", section);
            model.addAttribute("blocks", blockService.findAll());
            model.addAttribute("courses", courseService.findAll());
            model.addAttribute("faculties", facultyService.findAll());
            return "section/create";
        }

        sectionService.save(section);

        return "redirect:/section/";
    }

    @RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
    public String edit(@PathVariable(value="id") Long id, Model model) {
        Section section = sectionService.findOne(id);
        if(section == null) {
            // not found
            return "404";
        }
        model.addAttribute("section", section);
        model.addAttribute("blocks", blockService.findAll());
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("faculties", facultyService.findAll());

        return "section/update";
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    public String update(@PathVariable(value="id") Long id,
                         @Valid @ModelAttribute("section") Section section,
                         BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            model.addAttribute("blocks", blockService.findAll());
            model.addAttribute("courses", courseService.findAll());
            model.addAttribute("faculties", facultyService.findAll());
            return "section/update";
        }
        Section s = sectionService.findOne(id);
        s.setBuilding(section.getBuilding());
        s.setRoomNumber(section.getRoomNumber());
        s.setBlock(section.getBlock());
        s.setCourse(section.getCourse());
        s.setFaculty(section.getFaculty());
        sectionService.save(s);

        return "redirect:/section/";
    }

    @RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody AjaxResponse delete(@PathVariable(value="id") Long id) {
        Section section = sectionService.findOne(id);
        AjaxResponse response = new AjaxResponse();
        if(section != null) {
            try {
                sectionService.delete(id);
                response.success = true;
                response.msg = "Successfully deleted.";
            } catch(DataIntegrityViolationException ignore) {
                // Cannot remove block that is prerequisite of other block.
                response.success = false;
                response.msg = "Cannot remove block that is prerequisite of other block.";
            }
        }
        return response;
    }

}
