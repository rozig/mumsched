package mumsched.controller;

import mumsched.entity.*;
import mumsched.service.FacultyService;
import mumsched.service.StudentService;
import mumsched.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

@Controller
@RequestMapping(path = "/profile")
public class ProfileController {

    @Autowired
    UserService userService;

    @Autowired
    StudentService studentService;

    @Autowired
    FacultyService facultyService;

    @RequestMapping(value="/", method= RequestMethod.GET)
    public String myProfile(Model model) {

        String email = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.findByEmail(email);

        Profile profile = new Profile();

        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority(UserRoles.STUDENT.getValue()))) {
            Student student = studentService.findByUser(user);
            if (student != null) {
                profile = student;
                model.addAttribute("student", student);
            }
        } else if (authorities.contains(new SimpleGrantedAuthority(UserRoles.FACULTY.getValue()))) {
            Faculty faculty = facultyService.findByUser(user);
            if (faculty != null) {
                profile = faculty;
                model.addAttribute("faculty", faculty);
            }
        } else {
            return "redirect:/dashboard";
        }

        model.addAttribute("profile", profile);

        return "user/profile";
    }

}
