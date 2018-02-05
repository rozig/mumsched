package mumsched.controller;

import mumsched.entity.Profile;
import mumsched.entity.User;
import mumsched.entity.UserRoles;
import mumsched.service.FacultyService;
import mumsched.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = "/profile")
public class ProfileController {

    @Autowired
    StudentService studentService;

    @Autowired
    FacultyService facultyService;

    @RequestMapping(value="/", method= RequestMethod.GET)
    public String myProfile(Model model) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Profile profile;

        if (user.getRoles().contains(UserRoles.STUDENT.getValue())) {
            profile = studentService.findByUser(user);
        } else if (user.getRoles().contains(UserRoles.FACULTY.getValue())) {
            profile = facultyService.findByUser(user);
        } else {
            return "redirect:/dashboard";
        }

        model.addAttribute("profile", profile);

        return "user/profile";
    }

}
