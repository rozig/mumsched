package mumsched.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.context.request.WebRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import mumsched.AjaxResponse;

import mumsched.service.EntryService;
import mumsched.service.FacultyService;
import mumsched.service.UserService;
import mumsched.service.CourseService;
import mumsched.entity.Entry;
import mumsched.entity.Faculty;
import mumsched.entity.Role;
import mumsched.entity.User;
import mumsched.entity.UserRoles;
import mumsched.repository.RoleRepository;

@Controller
@RequestMapping(path="/faculty")
public class FacultyController {
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private EntryService entryService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JavaMailSender mailSender;

    @RequestMapping(value="/", method=RequestMethod.GET)
    public ModelAndView index() {
        List<Faculty> faculties = facultyService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("faculties", faculties);
        modelAndView.setViewName("faculty/index");
        return modelAndView;
    }

    @RequestMapping(value="/read/{id}", method=RequestMethod.GET)
    public String read(@PathVariable(value="id") Long id, Model model) {

        Faculty faculty = facultyService.findOne(id);
        if(faculty == null) {
            // not found
            return "404";
        }
        model.addAttribute("faculty", faculty);

        return "faculty/read";
    }

    @RequestMapping(value="/new", method=RequestMethod.GET)
    public ModelAndView newFaculty(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("faculty", new Faculty());
        modelAndView.addObject("user", new User());
        modelAndView.addObject("courses", courseService.findAll());
        modelAndView.setViewName("faculty/create");
        return modelAndView;
    }

    @RequestMapping(value="/create", method=RequestMethod.POST)
    public String create(
        @Valid @ModelAttribute("Faculty") Faculty faculty,
        BindingResult bindingResultFaculty,
        @Valid @ModelAttribute("User") User user,
        BindingResult bindingResultUser,
        Model model, WebRequest request
    ) {
        User userExists = userService.findByEmail(user.getEmail());
        if(userExists != null) {
            bindingResultUser.rejectValue("email", "error.user", "There is already a user registered with the email provided");
        }
        if(!bindingResultUser.hasErrors()) {
            String password = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(password);
            user.setActive(true);
            Role userRole = roleRepo.findByRole(UserRoles.FACULTY.getValue());
            user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
            userService.save(user);
            userService.saveNewFacultyUserWithProfile(user, faculty);

            String recipientAddress = user.getEmail();
            String subject = "Welcome to the MUMSched";
            String loginUrl
            = request.getContextPath() + "/login";
            String message = "Welcome to the MUMSched. Your username is \"" + user.getEmail() + "\" and password is \"" + user.getPassword() + "\".\nLogin at: " + loginUrl;

            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(recipientAddress);
            email.setSubject(subject);
            email.setText(message + " \r\n" + "http://localhost:8080" + loginUrl);
            mailSender.send(email);

            // redirect
            model.addAttribute("message", "Faculty has been registered successfully");
            return "redirect:/faculty/";
        }
        System.out.println(bindingResultUser.getAllErrors().toString());
        model.addAttribute("user", user);
        model.addAttribute("faculty", faculty);
        model.addAttribute("courses", courseService.findAll());
        return "faculty/create";
    }

    @RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
    public String edit(@PathVariable(value="id") Long id, Model model) {
        Faculty faculty = facultyService.findOne(id);
        if(faculty == null) {
            // not found
            return "404";
        }
        model.addAttribute("faculty", faculty);
        model.addAttribute("courses", courseService.findAll());

        return "faculty/update";
    }

    @RequestMapping(value="/update", method=RequestMethod.POST)
    public String update(
        @Valid @ModelAttribute("faculty") Faculty faculty,
        BindingResult resultFaculty,
        Model model, WebRequest request
    ) {
        if(!resultFaculty.hasErrors()) {
            User user = faculty.getUser();
            userService.save(user);
            facultyService.save(faculty);

            // redirect
            model.addAttribute("message", "Faculty info has been updated successfully");
            return "redirect:/faculty/";
        }
        model.addAttribute("faculty", faculty);
        model.addAttribute("courses", courseService.findAll());
        return "faculty/update";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody AjaxResponse delete(@PathVariable(value="id") Long id) {
        Faculty faculty = facultyService.findOne(id);
        Long userId = faculty.getUser().getId();
        AjaxResponse response = new AjaxResponse();
        if(faculty != null) {
            try {
                facultyService.delete(id);
                userService.delete(userId);
                response.success = true;
                response.msg = "Successfully deleted.";
            } catch(DataIntegrityViolationException ignore) {
                response.success = false;
                response.msg = "Cannot remove faculty that is registered in active section.";
            }
        }
        return response;
    }
}