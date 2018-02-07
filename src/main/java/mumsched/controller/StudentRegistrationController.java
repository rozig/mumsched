package mumsched.controller;

import mumschedRegistrationSubsystem.StudentRegistrationSubsystemFacade;
import mumschedRegistrationSubsystem.StudentRegistrationSubsystem;
import mumsched.AjaxResponse;
import mumsched.entity.Course;
import mumsched.entity.Section;
import mumsched.entity.Student;
import mumsched.entity.User;
import mumsched.entity.UserRoles;
import mumsched.service.SectionService;
import mumsched.service.StudentService;
import mumsched.service.UserService;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping(path = "/registersection")
public class StudentRegistrationController {
    @Autowired
    private StudentRegistrationSubsystem facade;
    
    @Autowired
    UserService userService;
    
    @Autowired
    SectionService sectionService;

    @Autowired
    StudentService studentService;
    
    @RequestMapping(value="/", method=RequestMethod.GET)
    public String index(Model model) {
    	Student student;
    	String email = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.findByEmail(email);

        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority(UserRoles.STUDENT.getValue()))) {
        	student = studentService.findByUser(user);
        }else {
        	return "redirect:/dashboard";
        }
    	
        model.addAttribute("entry", student.getEntry());
        model.addAttribute("sections", student.getRegisteredSections());
        
        return "studentRegistration/index";
    }
    
    @RequestMapping(value="/view", method=RequestMethod.GET)
    public String view(Model model) {
    	Student student;
    	String email = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.findByEmail(email);

        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority(UserRoles.STUDENT.getValue()))) {
        	student = studentService.findByUser(user);
        }else {
        	return "redirect:/dashboard";
        }
    	
        model.addAttribute("sections", student.getRegisteredSections());
        
        return "studentRegistration/view";
    }
    
    @RequestMapping(value = "/register/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody AjaxResponse create(@PathVariable(value = "id") Long id) {
        Section section = sectionService.findOne(id);
        
        Student student;
    	String email = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.findByEmail(email);

        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        AjaxResponse response = new AjaxResponse();
        
        if (authorities.contains(new SimpleGrantedAuthority(UserRoles.STUDENT.getValue()))) {
        	student = studentService.findByUser(user);
        }else {
        	response.success = false;
            response.msg = "This is not a student.";
            return response;
        }
        
        response = facade.registerToSection(section, student);
        
        return response;
    }
    
    

}
