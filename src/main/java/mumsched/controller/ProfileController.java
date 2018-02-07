package mumsched.controller;

import mumsched.entity.*;
import mumsched.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
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

    @Autowired
    CourseService courseService;

    @Autowired
    EntryService entryService;

    private Profile profile;
    private Student student;
    private Faculty faculty;
    private boolean isFaculty = false;
    private boolean isStudent = false;

    @RequestMapping(value="/", method= RequestMethod.GET)
    public String myProfile(Model model) {

        if (!initProfile(model))
            return "redirect:/dashboard";

        return "user/profile";
    }
    
    @RequestMapping(value="/changePassword", method= RequestMethod.GET)
    public String changePassword(Model model) {
    	String email = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.findByEmail(email);
        
        model.addAttribute("profile", user);
        
        if (!initProfile(model))
            return "redirect:/dashboard";

        return "user/changePassword";
    }
    
    @RequestMapping(value="/updatePassword", method= RequestMethod.POST)
    public String updatePassword(@Valid @ModelAttribute("User") User user, BindingResult resultFaculty, Model model) {

		if (!initProfile(model))
		return "redirect:/dashboard";
		
		faculty.setUser(this.faculty.getUser());
		userService.save(user);
		return "redirect:/profile/";
	}

    @RequestMapping(value="/edit", method= RequestMethod.GET)
    public String editProfile(Model model) {

        if (!initProfile(model))
            return "redirect:/dashboard";

        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("entries", entryService.findAll());

        return "user/updateProfile";
    }

    @RequestMapping(value="/update", method= RequestMethod.POST)
    public String updateProfile(@Valid @ModelAttribute("Faculty") Faculty faculty, BindingResult resultFaculty,
                                @Valid @ModelAttribute("Student") Student student, BindingResult resultStudent,
                                Model model) {

        if (!initProfile(model))
            return "redirect:/dashboard";

        if (isStudent) {
            student.setUser(this.student.getUser());
            studentService.save(student);
            return "redirect:/profile/";
        } else if (isFaculty) {
            faculty.setUser(this.faculty.getUser());
            facultyService.save(faculty);
            return "redirect:/profile/";
        }

        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("entries", entryService.findAll());

        return "user/updateProfile";
    }

    private boolean initProfile(Model model) {

        String email = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.findByEmail(email);

        this.profile = new Profile();

        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority(UserRoles.STUDENT.getValue()))) {
            this.isStudent = true;
            this.student = studentService.findByUser(user);
            if (student != null) {
                this.profile = student;
                model.addAttribute("student", student);
            }
        } else if (authorities.contains(new SimpleGrantedAuthority(UserRoles.FACULTY.getValue()))) {
            this.isFaculty = true;
            this.faculty = facultyService.findByUser(user);
            if (faculty != null) {
                this.profile = faculty;
                model.addAttribute("faculty", faculty);
            }
        } else {
            return false;
        }

        model.addAttribute("profile", profile);

        return true;
    }
}
