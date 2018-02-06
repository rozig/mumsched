package mumsched.controller;

import mumschedRegistrationSubsystem.StudentRegistrationSubsystemFacade;
import mumsched.entity.Section;
import mumsched.entity.Student;
import mumsched.entity.User;
import mumsched.entity.UserRoles;
import mumsched.service.StudentService;
import mumsched.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
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
    private StudentRegistrationSubsystemFacade facade;
    
    @Autowired
    UserService userService;

    @Autowired
    StudentService studentService;

    @RequestMapping(value="/", method=RequestMethod.GET)
    public String index(Model model) {
    	
    	String email = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.findByEmail(email);

        Student student;

        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority(UserRoles.STUDENT.getValue()))) {
        	student = studentService.findByUser(user);
        }else {
        	return "redirect:/dashboard";
        }
    	
        List<Section> sections = facade.getSectionList( student );
        model.addAttribute("sections", sections);
        
        return "studentRegistration/index";
    }

}
